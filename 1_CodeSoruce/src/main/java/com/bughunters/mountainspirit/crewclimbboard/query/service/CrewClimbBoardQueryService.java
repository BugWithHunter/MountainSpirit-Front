package com.bughunters.mountainspirit.crewclimbboard.query.service;

import com.bughunters.mountainspirit.crewclimbboard.query.dto.CrewClimbBoardAndMountainAndCrewMemberDTO;
import com.bughunters.mountainspirit.crewclimbboard.query.dto.CrewClimbBoardAndMountainDTO;
import com.bughunters.mountainspirit.crewclimbboard.query.dto.MyCrewClimbBoardListDTO;

import java.util.List;

public interface CrewClimbBoardQueryService {
    List<CrewClimbBoardAndMountainDTO> findAllCrewClimbBoardByCrewId(Long crewId);

    CrewClimbBoardAndMountainAndCrewMemberDTO findOneCrewClimbBoardByCrewClimbBoardId(Long crewClimbBoardId);

    List<MyCrewClimbBoardListDTO> findMyCrewClimbBoardByCrewMemberId(Long crewMemberId);
}
