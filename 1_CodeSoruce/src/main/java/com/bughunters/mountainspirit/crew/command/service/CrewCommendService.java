package com.bughunters.mountainspirit.crew.command.service;


import com.bughunters.mountainspirit.crew.command.dto.CrewDeleteDTO;
import com.bughunters.mountainspirit.crew.command.dto.CrewModifyDTO;
import com.bughunters.mountainspirit.crew.command.dto.CrewRegistDTO;
import com.bughunters.mountainspirit.crew.command.entity.Crew;
import com.bughunters.mountainspirit.crew.command.repository.CrewCommendRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class CrewCommendService implements CrewCommendServiceInter {
    private final CrewCommendRepository crewCommendRepository;
    ModelMapper modelMapper;

    @Autowired
    public CrewCommendService(CrewCommendRepository crewCommendRepository, ModelMapper modelMapper) {
        this.crewCommendRepository = crewCommendRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void insertCrew(CrewRegistDTO crewRegistDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        // 기본값 세팅
        Crew crew = setDefaultCrewValues(modelMapper.map(crewRegistDTO, Crew.class));

//        log.info("crewEntity : {}", crew);

        crewCommendRepository.save(crew);
    }

    @Override
    @Transactional
    public void modifyCrew(CrewModifyDTO crewModifyDTO) {
        Crew crew = crewCommendRepository.findById(crewModifyDTO.getId()).get();
        modifyCrewEntity(crewModifyDTO, crew);
    }

    @Override
    @Transactional
    public void deleteCrew(CrewDeleteDTO crewDeleteDTO) {
        Crew crew = crewCommendRepository.findById(crewDeleteDTO.getId()).get();
        crew.setCrewIsDeleted('Y');
    }




    // ////////////////////////////클래스 내에서 사용되는 함수들////////////////////////////

    private Crew setDefaultCrewValues(Crew crew) {
        LocalDateTime now = LocalDateTime.now();

        crew.setCrewIsRecruit('Y');
        crew.setCrewCreateDate(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        if (crew.getCrewMaxPeople() == null) crew.setCrewMaxPeople(5);
        crew.setCrewIsDeleted('N');
        crew.setCrewBanDate(30);
        crew.setCrewRankGauge(1);

        return crew;
    }

    private static void modifyCrewEntity(CrewModifyDTO crewModifyDTO, Crew crew) {
        if (!crew.getCrewName().equals(crewModifyDTO.getCrewName()))
            crew.setCrewName(crewModifyDTO.getCrewName());

        if (!(crew.getCrewIsRecruit() == crewModifyDTO.getCrewIsRecruit()))
            crew.setCrewIsRecruit(crewModifyDTO.getCrewIsRecruit());

        if (!crew.getCrewMaxPeople().equals(crewModifyDTO.getCrewMaxPeople()))
            crew.setCrewMaxPeople(crewModifyDTO.getCrewMaxPeople());

        if (!crew.getCrewIntro().equals(crewModifyDTO.getCrewIntro()))
            crew.setCrewIntro(crewModifyDTO.getCrewIntro());

        if (!(crew.getCrewBanDate() == crewModifyDTO.getCrewBanDate()))
            crew.setCrewBanDate(crewModifyDTO.getCrewBanDate());
    }
}
