package com.bughunters.mountainspirit.crewclimbboard.query.mapper;

import com.bughunters.mountainspirit.crewclimbboard.query.dto.CrewClimbBoardAndMountainAndCrewMemberDTO;
import com.bughunters.mountainspirit.crewclimbboard.query.dto.CrewClimbBoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CrewClimbBoardQueryMapper {
    List<CrewClimbBoardDTO> findAllCrewClimbBoardByCrewId(Long crewId);

    CrewClimbBoardAndMountainAndCrewMemberDTO findOneCrewClimbBoardByCrewClimbBoardId(Long crewClimbBoardId);
}
