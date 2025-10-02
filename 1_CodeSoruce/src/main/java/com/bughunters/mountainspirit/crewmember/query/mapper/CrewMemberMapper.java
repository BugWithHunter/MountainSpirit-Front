package com.bughunters.mountainspirit.crewmember.query.mapper;

import com.bughunters.mountainspirit.crewmember.query.dto.CrewAndMemberDTO;
import com.bughunters.mountainspirit.crewmember.query.dto.CrewApplyListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CrewMemberMapper {
    CrewApplyListDTO findAllCrewAppliedByCrewId(long crewId);

    String checkCrewBannedDateByCrewIdAndCumId(Long crewId, Long cumId);

    List<CrewAndMemberDTO> findCrewMemberListByCrewId(long crewId);
}
