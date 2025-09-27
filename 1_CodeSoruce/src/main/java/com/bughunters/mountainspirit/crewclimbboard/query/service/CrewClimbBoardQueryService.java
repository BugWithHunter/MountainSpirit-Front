package com.bughunters.mountainspirit.crewclimbboard.query.service;

import com.bughunters.mountainspirit.crewclimbboard.query.dto.CrewClimbBoardAndMountainAndCrewMemberDTO;
import com.bughunters.mountainspirit.crewclimbboard.query.dto.CrewClimbBoardDTO;

import java.util.List;

public interface CrewClimbBoardQueryService {
    List<CrewClimbBoardDTO> findAllCrewClimbBoardByCrewId(Long crewId);

    CrewClimbBoardAndMountainAndCrewMemberDTO findOneCrewClimbBoardByCrewClimbBoardId(Long crewClimbBoardId);
}
