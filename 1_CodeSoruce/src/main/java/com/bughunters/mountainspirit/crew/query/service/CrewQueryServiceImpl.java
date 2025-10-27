package com.bughunters.mountainspirit.crew.query.service;

//import com.bughunters.mountainspirit.crew.command.infrastructure.TestClient;
import com.bughunters.mountainspirit.crew.query.dto.CrewDTO;
import com.bughunters.mountainspirit.crew.query.dto.CrewInfoDTO;
import com.bughunters.mountainspirit.crew.query.mapper.CrewMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CrewQueryServiceImpl implements CrewQueryService {
    private CrewMapper crewMapper;
//    TestClient testClient;

    @Autowired
    public CrewQueryServiceImpl(CrewMapper crewMapper){
//                                TestClient testClient) {
        this.crewMapper = crewMapper;
//        this.testClient = testClient;
    }

    @Override
    public List<CrewDTO> findAllCrews() {return crewMapper.findAllCrews();}

    @Override
    public CrewInfoDTO findOneCrewById(Long crewId) {
        return crewMapper.findOneCrewById(crewId);
    }
}
