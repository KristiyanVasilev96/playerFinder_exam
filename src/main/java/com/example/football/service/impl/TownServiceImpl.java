package com.example.football.service.impl;

import com.example.football.models.dto.TownSeedDto;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
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
public class TownServiceImpl implements TownService {

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;

    private static final String PATH_OF_TOWN = "src/main/resources/files/json/towns.json";

    public TownServiceImpl(Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, TownRepository townRepository) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
    }


    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(PATH_OF_TOWN));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(gson.fromJson(readTownsFileContent(), TownSeedDto[].class))
                .filter(townSeedDto -> {
                    boolean isValid=validationUtil.isValid(townSeedDto)
                            && !isEntityExistByTownName(townSeedDto.getName());

                    sb.append(isValid ? String.format("Successfully imported Town %s - %d",townSeedDto.getName(),townSeedDto.getPopulation())
                            :"Invalid town").append(System.lineSeparator());

                    return isValid;
                }).map(townSeedDto -> modelMapper.map(townSeedDto,Town.class))
                .forEach(townRepository::save);

        return sb.toString();
    }

    @Override
    public Town findByTownByName(String townName) {
        return townRepository.findByName(townName);
    }

    private boolean isEntityExistByTownName(String name) {
        return townRepository.existsByName(name);
    }
}
