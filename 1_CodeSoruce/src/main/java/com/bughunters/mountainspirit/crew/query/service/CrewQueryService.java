package com.bughunters.mountainspirit.crew.query.service;

import com.bughunters.mountainspirit.crew.query.dto.CrewDTO;
import com.bughunters.mountainspirit.crew.query.mapper.CrewMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CrewQueryService implements CrewQueryServiceInter {
    private CrewMapper crewMapper;

    @Autowired
    public CrewQueryService(CrewMapper crewMapper) {
        this.crewMapper = crewMapper;
    }

    @Override
    public List<CrewDTO> findAllCrews() {
        return crewMapper.findAllCrews();
    }

    @Override
    public CrewDTO findOneCrewById(Long crewId) {
        return crewMapper.findOneCrewById(crewId);
    }
}
