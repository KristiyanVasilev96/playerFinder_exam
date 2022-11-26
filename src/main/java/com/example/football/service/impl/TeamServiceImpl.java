package com.example.football.service.impl;

import com.example.football.models.dto.TeamSeedDto;
import com.example.football.models.entity.Team;
import com.example.football.repository.TeamRepository;
import com.example.football.service.TeamService;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class TeamServiceImpl implements TeamService {
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final TeamRepository teamRepository;
    private final ValidationUtil validationUtil;

    private final TownService townService;

    private static final String PATH_OF_TEAMS = "src/main/resources/files/json/teams.json";

    public TeamServiceImpl(Gson gson, ModelMapper modelMapper, TeamRepository teamRepository, ValidationUtil validationUtil, TownService townService) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.teamRepository = teamRepository;
        this.validationUtil = validationUtil;
        this.townService = townService;
    }


    @Override
    public boolean areImported() {
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(PATH_OF_TEAMS));
    }

    @Override
    public String importTeams() throws IOException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson.fromJson(readTeamsFileContent(), TeamSeedDto[].class))
                .filter(teamSeedDto -> {
                    boolean isValid = validationUtil.isValid(teamSeedDto)
                            && !isEntityExistByTeamName(teamSeedDto.getName());
                    sb.append(isValid ? String.format("Successfully imported Team %s - %d", teamSeedDto.getName(), teamSeedDto.getFanBase())
                            : "Invalid team").append(System.lineSeparator());

                    return isValid;
                }).map(teamSeedDto -> {
                    Team team = modelMapper.map(teamSeedDto, Team.class);
                    team.setTown(townService.findByTownByName(teamSeedDto.getTownName()));

                    return team;


                }).forEach(teamRepository::save);

        return sb.toString();
    }

    @Override
    public Team findByTeamName(String teamName) {
        return teamRepository.findByName(teamName);
    }

    private boolean isEntityExistByTeamName(String name) {
        return teamRepository.existsByName(name);
    }
}
