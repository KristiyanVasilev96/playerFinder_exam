package com.example.football.service.impl;

import com.example.football.models.dto.StatsSeedRootDto;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.ValidationUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class StatServiceImpl implements StatService {
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final StatRepository statRepository;

    private static final String PATH_OF_STATS = "src/main/resources/files/xml/stats.xml";

    public StatServiceImpl(XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil, StatRepository statRepository) {
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.statRepository = statRepository;
    }


    @Override
    public boolean areImported() {
        return statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(PATH_OF_STATS));
    }

    @Override
    public String importStats() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        xmlParser.fromFile(PATH_OF_STATS, StatsSeedRootDto.class)
                .getStat().stream().filter(statsSeedDto -> {

                    boolean isValid = validationUtil.isValid(statsSeedDto)
                            && !isEntityExistByShootingEndurancePassing(statsSeedDto.getEndurance(), statsSeedDto.getPassing(), statsSeedDto.getShooting());

                    sb.append(isValid ? String.format("Successfully imported Stat %.2f - %.2f - %.2f", statsSeedDto.getShooting(), statsSeedDto.getPassing(), statsSeedDto.getEndurance())
                            : "Invalid Stat").append(System.lineSeparator());

                    return isValid;
                }).map(statsSeedDto -> modelMapper.map(statsSeedDto, Stat.class))
                .forEach(statRepository::save);
        return sb.toString();
    }

    @Override
    public Stat findStatById(Long id) {
        return statRepository.findStatById(id);
    }

    private boolean isEntityExistByShootingEndurancePassing(double endurance, double passing, double shooting) {
        return statRepository.existsByEnduranceAndShootingAndPassing(endurance, shooting, passing);
    }
}
