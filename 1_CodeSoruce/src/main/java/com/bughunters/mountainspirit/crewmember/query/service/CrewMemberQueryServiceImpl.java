package com.bughunters.mountainspirit.crewmember.query.service;

import com.bughunters.mountainspirit.crewmember.query.dto.CrewApplyListDTO;
import com.bughunters.mountainspirit.crewmember.query.mapper.CrewMemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class CrewMemberQueryServiceImpl implements CrewMemberQueryService {
    private CrewMemberMapper crewMemberMapper;

    @Autowired
    public CrewMemberQueryServiceImpl (CrewMemberMapper crewMemberMapper) {
        this.crewMemberMapper = crewMemberMapper;
    }

    @Override
    @Transactional
    public CrewApplyListDTO findAllCrewApplied(CrewApplyListDTO crewApplyListDTO) {
        long crewId = crewApplyListDTO.getCrewId();
        crewApplyListDTO = crewMemberMapper.findAllCrewAppliedByCrewId(crewId);
        return crewApplyListDTO;
    }

    @Override
    @Transactional
    public Integer checkCrewApplyIsBanned(Long crewId, Long cumId) {
        
        return 0;
    }
}
