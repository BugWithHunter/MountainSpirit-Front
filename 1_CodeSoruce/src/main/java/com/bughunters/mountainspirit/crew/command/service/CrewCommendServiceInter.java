package com.bughunters.mountainspirit.crew.command.service;

import com.bughunters.mountainspirit.crew.command.dto.CrewDeleteDTO;
import com.bughunters.mountainspirit.crew.command.dto.CrewModifyDTO;
import com.bughunters.mountainspirit.crew.command.dto.CrewRegistDTO;

public interface CrewCommendServiceInter {
    void insertCrew(CrewRegistDTO crewRegistDTO);

    void modifyCrew(CrewModifyDTO crewModifyDTO);

    void deleteCrew(CrewDeleteDTO crewDeleteDTO);
}
