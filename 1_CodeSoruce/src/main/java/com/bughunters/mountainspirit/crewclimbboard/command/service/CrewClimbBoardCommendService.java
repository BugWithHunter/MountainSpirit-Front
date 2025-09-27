package com.bughunters.mountainspirit.crewclimbboard.command.service;

import com.bughunters.mountainspirit.crewclimbboard.command.dto.CrewClimbBoardDTO;

public interface CrewClimbBoardCommendService {
    void registCrewClimbBoard(CrewClimbBoardDTO crewClimbBoardDTO);

    void modifyCrewClimbBoard(CrewClimbBoardDTO crewClimbBoardDTO);

    void deleteCrewClimbBoardById(Long id);

    void closeCrewClimbBoardApplyById(Long id);
}
