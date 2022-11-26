package com.example.football.service.impl;

import com.example.football.models.dto.PlayerSeedRootDto;
import com.example.football.models.entity.Player;
import com.example.football.repository.PlayerRepository;
import com.example.football.service.PlayerService;
import com.example.football.service.StatService;
import com.example.football.service.TeamService;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final PlayerRepository playerRepository;

    private final TeamService teamService;
    private final TownService townService;
    private final StatService statService;


    private static final String PATH_OF_PLAYER = "src/main/resources/files/xml/players.xml";

    public PlayerServiceImpl(XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper, PlayerRepository playerRepository, TeamService teamService, TownService townService, StatService statService) {
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.playerRepository = playerRepository;
        this.teamService = teamService;
        this.townService = townService;
        this.statService = statService;
    }


    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PATH_OF_PLAYER));
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        xmlParser.fromFile(PATH_OF_PLAYER, PlayerSeedRootDto.class)
                .getPlayers().stream().filter(playerSeedDto -> {

                    boolean isValid = validationUtil.isValid(playerSeedDto)
                            && !isEmailExist(playerSeedDto.getEmail());
                    sb.append(isValid ? String.format("Successfully imported Player %s %s - %s", playerSeedDto.getFirstName(), playerSeedDto.getLastName(), playerSeedDto.getPosition())
                            : "Invalid player").append(System.lineSeparator());

                    return isValid;
                }).map(playerSeedDto -> {
                    Player player=modelMapper.map(playerSeedDto,Player.class);

                    player.setTown(townService.findByTownByName(playerSeedDto.getTownName().getName()));
                    player.setTeam(teamService.findByTeamName(playerSeedDto.getTeamName().getName()));
                    player.setStat(statService.findStatById(playerSeedDto.getId().getId()));

                    return player;

                }).forEach(playerRepository::save);

        return sb.toString();
    }

    private boolean isEmailExist(String email) {
        return playerRepository.existsByEmail(email);
    }

    @Override
    public String exportBestPlayers() {
        StringBuilder sb= new StringBuilder();
        playerRepository.findByPlayerFirstNameLastNamePositionTeamName()
                .stream().forEach(player -> {
                   sb.append(String.format("Player - %s %s\n" +
                            "\tPosition - %s\n" +
                            "\tTeam - %s\n" +
                            "\tStadium - %s\n",player.getFirstName(),player.getLastName(),player.getPosition(),player.getTeam().getName(),player.getTeam().getStadiumName())).append(System.lineSeparator());
                });

        return sb.toString();
    }
}
