package com.bughunters.mountainspirit.mountain.command.service;

import com.bughunters.mountainspirit.mountain.command.dto.ResponseCourseDTO;

import java.util.List;

public interface MountainService {
    void mergeAllMountains();

    void mergeAllCourses();

    List<ResponseCourseDTO> selectCoursesByPoiId(String poiId);

    String getMountainName(String frtrlId);
}
