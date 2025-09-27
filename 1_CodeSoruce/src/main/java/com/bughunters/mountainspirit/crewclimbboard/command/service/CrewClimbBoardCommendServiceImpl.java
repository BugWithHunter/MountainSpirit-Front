package com.bughunters.mountainspirit.crewclimbboard.command.service;

import com.bughunters.mountainspirit.crewclimbboard.command.dto.CrewClimbBoardDTO;
import com.bughunters.mountainspirit.crewclimbboard.command.entity.CrewClimbBoard;
import com.bughunters.mountainspirit.crewclimbboard.command.repository.CrewClimbBoardCommendRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CrewClimbBoardCommendServiceImpl implements CrewClimbBoardCommendService {
    ModelMapper modelMapper;
    private CrewClimbBoardCommendRepository crewClimbBoardCommendRepository;

    @Autowired
    public CrewClimbBoardCommendServiceImpl(CrewClimbBoardCommendRepository crewClimbBoardCommendRepository,
                                            ModelMapper modelMapper) {
        this.crewClimbBoardCommendRepository = crewClimbBoardCommendRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registCrewClimbBoard(CrewClimbBoardDTO crewClimbBoardDTO) {
        CrewClimbBoard crewClimbBoard = modelMapper.map(crewClimbBoardDTO, CrewClimbBoard.class);
        log.info("Service 크루 모집 일정 넘어온거 맵핑 값 : {}", crewClimbBoard);
        crewClimbBoard.setCrewClimbIsDeleted('N');
        crewClimbBoard.setCrewClimbIsEnded('N');
        crewClimbBoardCommendRepository.save(crewClimbBoard);
    }
}
