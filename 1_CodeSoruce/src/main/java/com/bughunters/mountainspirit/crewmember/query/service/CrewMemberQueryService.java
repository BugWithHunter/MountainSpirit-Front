package com.bughunters.mountainspirit.crewmember.query.service;

import com.bughunters.mountainspirit.crewmember.query.dto.CrewApplyListDTO;

import java.util.List;

public interface CrewMemberQueryService {
    CrewApplyListDTO findAllCrewApplied(CrewApplyListDTO crewApplyListDTO);

    Integer checkCrewApplyIsBanned(Long crewId, Long cumId);
}
