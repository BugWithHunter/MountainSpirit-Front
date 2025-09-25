package com.bughunters.mountainspirit.climbhistory.command.service;

import com.bughunters.mountainspirit.climbhistory.command.dto.FindClimbCheckDTO;
import com.bughunters.mountainspirit.climbhistory.command.dto.RequestStartClimbMountainDTO;

public interface ClimbHistoryService {
    FindClimbCheckDTO startClimbMountain(RequestStartClimbMountainDTO request);
}
