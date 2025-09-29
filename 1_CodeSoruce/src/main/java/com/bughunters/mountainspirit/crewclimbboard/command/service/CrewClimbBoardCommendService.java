package com.bughunters.mountainspirit.crewclimbboard.command.service;

import com.bughunters.mountainspirit.crewclimbboard.command.dto.CrewClimbBoardApplyDTO;
import com.bughunters.mountainspirit.crewclimbboard.command.dto.CrewClimbBoardCancelDTO;
import com.bughunters.mountainspirit.crewclimbboard.command.dto.CrewClimbBoardDTO;
import com.bughunters.mountainspirit.crewclimbboard.command.dto.CrewClimbRecordRegistDTO;

public interface CrewClimbBoardCommendService {
    void registCrewClimbBoard(CrewClimbBoardDTO crewClimbBoardDTO);

    void modifyCrewClimbBoard(CrewClimbBoardDTO crewClimbBoardDTO);

    void deleteCrewClimbBoardById(Long id);

    void closeCrewClimbBoardById(Long id);

    void applyCrewClimbBoard(CrewClimbBoardApplyDTO crewClimbBoardApplyDTO);

    void cancelCrewClimb(CrewClimbBoardCancelDTO crewClimbBoardCancelDTO);
}
