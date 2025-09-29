package com.bughunters.mountainspirit.crew.query.service;

import com.bughunters.mountainspirit.crew.query.dto.CrewDTO;
import com.bughunters.mountainspirit.crew.query.dto.CrewInfoDTO;

import java.util.List;

public interface CrewQueryService {
    List<CrewDTO> findAllCrews();

    CrewInfoDTO findOneCrewById(Long crewId);
}
