package com.bughunters.mountainspirit.crew.query.mapper;

import com.bughunters.mountainspirit.crew.query.dto.CrewDTO;
import com.bughunters.mountainspirit.crew.query.dto.CrewInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CrewMapper {
    List<CrewDTO> findAllCrews();

    CrewInfoDTO findOneCrewById(Long crewId);
}
