package com.bughunters.mountainspirit.mountain.query.service;

import com.bughunters.mountainspirit.mountain.query.dto.CourseDTO;
import com.bughunters.mountainspirit.mountain.query.dto.MountainDTO;

import java.util.List;

public interface MountainSearchService {
    List<MountainDTO> getAllMountainInfo();

    List<MountainDTO> getSearchMountainInfo(String mountainName);

    List<CourseDTO> getAllCourseInfo();

    List<CourseDTO> getSearchCourseInfo(String mountainName);
}
