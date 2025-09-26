package com.bughunters.mountainspirit.stamp.command.service;

import com.bughunters.mountainspirit.climbhistory.command.dto.RequestStartClimbMountainDTO;
import com.bughunters.mountainspirit.stamp.command.dto.StampWithCourseAndMountainDTO;

public interface StampService {
    StampWithCourseAndMountainDTO copleteClimbingMountain(RequestStartClimbMountainDTO request);
}
