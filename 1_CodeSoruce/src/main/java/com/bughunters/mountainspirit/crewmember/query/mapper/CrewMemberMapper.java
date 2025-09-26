package com.bughunters.mountainspirit.crewmember.query.mapper;

import com.bughunters.mountainspirit.crewmember.query.dto.CrewApplyListDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CrewMemberMapper {
    CrewApplyListDTO findAllCrewAppliedByCrewId(long crewId);
}
