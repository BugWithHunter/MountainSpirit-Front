package com.bughunters.mountainspirit.crew.query.service;

import com.bughunters.mountainspirit.crew.query.dto.CrewDTO;
import com.bughunters.mountainspirit.crew.query.mapper.CrewMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class CrewInfoService implements CrewService {
    private final CrewMapper crewMapper;

    @Autowired
    public CrewInfoService(CrewMapper crewMapper) {
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
