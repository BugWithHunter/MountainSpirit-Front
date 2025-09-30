package com.bughunters.mountainspirit.stamp.query.service;

import com.bughunters.mountainspirit.stamp.query.dto.CourseStampDTO;
import com.bughunters.mountainspirit.stamp.query.dto.MountainStampDTO;

import java.util.List;

public interface StampService {
    List<MountainStampDTO> getMountainStamp(long memberId);

    List<CourseStampDTO> getCourseStamp(long memberId);
}
