package com.bughunters.mountainspirit.crewmember.query.service;

import com.bughunters.mountainspirit.crewmember.query.dto.CrewAndMemberDTO;
import com.bughunters.mountainspirit.crewmember.query.dto.CrewApplyListDTO;

import java.util.List;

public interface CrewMemberQueryService {
    CrewApplyListDTO findAllCrewApplied(CrewApplyListDTO crewApplyListDTO);

    boolean checkCrewApplyIsBanned(Long crewId, Long cumId);

    List<CrewAndMemberDTO> findCrewMemberListByCrewId(long crewId);
}
