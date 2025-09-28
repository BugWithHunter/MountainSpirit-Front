package com.bughunters.mountainspirit.stamp.command.service;

import com.bughunters.mountainspirit.stamp.command.dto.RequestSubmmitClimbMountainDTO;
import com.bughunters.mountainspirit.stamp.command.dto.StampWithCourseAndMountainDTO;

public interface StampService {
    StampWithCourseAndMountainDTO copleteClimbingMountain(RequestSubmmitClimbMountainDTO request);
}
