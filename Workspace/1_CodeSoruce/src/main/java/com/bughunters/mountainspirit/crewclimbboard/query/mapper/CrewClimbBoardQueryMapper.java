package com.bughunters.mountainspirit.crewclimbboard.query.mapper;

import com.bughunters.mountainspirit.crewclimbboard.query.dto.CrewClimbBoardAndMountainAndCrewMemberDTO;
import com.bughunters.mountainspirit.crewclimbboard.query.dto.CrewClimbBoardAndMountainDTO;
import com.bughunters.mountainspirit.crewclimbboard.query.dto.MyCrewClimbBoardListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CrewClimbBoardQueryMapper {
    List<CrewClimbBoardAndMountainDTO> findAllCrewClimbBoardByCrewId(Long crewId);

    CrewClimbBoardAndMountainAndCrewMemberDTO findOneCrewClimbBoardByCrewClimbBoardId(Long crewClimbBoardId);

    List<MyCrewClimbBoardListDTO> findMyCrewClimbBoardByMemberId(Long memberId);
}
