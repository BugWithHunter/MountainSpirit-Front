package com.bughunters.mountainspirit.stamp.command.service;

import com.bughunters.mountainspirit.stamp.command.dto.RequestSubmmitClimbMountainDTO;
import com.bughunters.mountainspirit.stamp.command.dto.ResponseCourseDTO;
import com.bughunters.mountainspirit.stamp.command.dto.StampWithCourseAndMountainDTO;
import com.bughunters.mountainspirit.stamp.command.entity.CourseStamp;

import java.util.List;

public interface StampService {
    StampWithCourseAndMountainDTO copleteClimbingMountain(RequestSubmmitClimbMountainDTO request);

    List<CourseStamp> findCourseStamp(Long memberId);
}
