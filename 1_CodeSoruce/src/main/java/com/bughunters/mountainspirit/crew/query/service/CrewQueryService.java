package com.bughunters.mountainspirit.crew.query.service;

import com.bughunters.mountainspirit.crew.query.dto.CrewDTO;

import java.util.List;

public interface CrewQueryService {
    List<CrewDTO> findAllCrews();

    CrewDTO findOneCrewById(Long crewId);
}
