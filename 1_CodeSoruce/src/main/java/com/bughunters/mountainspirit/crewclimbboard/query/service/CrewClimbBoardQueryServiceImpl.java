package com.bughunters.mountainspirit.crewclimbboard.query.service;

import com.bughunters.mountainspirit.crewclimbboard.query.mapper.CrewClimbBoardQueryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CrewClimbBoardQueryServiceImpl implements CrewClimbBoardQueryService{
    private CrewClimbBoardQueryMapper crewClimbBoardQueryMapper;

    @Autowired
    public CrewClimbBoardQueryServiceImpl(CrewClimbBoardQueryMapper crewClimbBoardQueryMapper){
        this.crewClimbBoardQueryMapper = crewClimbBoardQueryMapper;
    }
}
