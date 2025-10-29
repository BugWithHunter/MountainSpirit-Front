package com.bughunters.mountainspirit.stamp.query.service;

import com.bughunters.mountainspirit.stamp.query.dto.CourseStampDTO;
import com.bughunters.mountainspirit.stamp.query.dto.MountainStampDTO;
import com.bughunters.mountainspirit.stamp.query.mapper.CourseStampGetMapper;
import com.bughunters.mountainspirit.stamp.query.mapper.MountainStampGetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("StampQueryService")
public class StampServiceImpl implements StampService{
    private MountainStampGetMapper mountainStampGetMapper;
    private CourseStampGetMapper courseStampGetMapper;

    @Autowired
    public StampServiceImpl(MountainStampGetMapper mountainStampGetMapper, CourseStampGetMapper courseStampGetMapper) {
        this.mountainStampGetMapper = mountainStampGetMapper;
        this.courseStampGetMapper = courseStampGetMapper;
    }


    @Override
    public List<MountainStampDTO> getMountainStamp(long memberId) {
        List<MountainStampDTO> mountainStampList = mountainStampGetMapper.selectMountainStampById(memberId);
        return mountainStampList;
    }

    @Override
    public List<CourseStampDTO> getCourseStamp(long memberId) {
        List<CourseStampDTO> courseStampList = courseStampGetMapper.selectCourseStampById(memberId);
        return courseStampList;
    }
}
