package com.bughunters.mountainspirit.climbhistory.command.service;

import com.bughunters.mountainspirit.climbhistory.command.dto.FindClimbCheckDTO;
import com.bughunters.mountainspirit.climbhistory.command.dto.RequestSubmmitClimbMountainDTO;

public interface ClimbHistoryService {
    FindClimbCheckDTO startClimbMountain(RequestSubmmitClimbMountainDTO request);

    FindClimbCheckDTO completeClimbMountain(RequestSubmmitClimbMountainDTO request);
}
