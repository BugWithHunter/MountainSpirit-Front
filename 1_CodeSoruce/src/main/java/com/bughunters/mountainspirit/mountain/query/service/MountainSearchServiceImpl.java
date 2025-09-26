package com.bughunters.mountainspirit.mountain.query.service;

import com.bughunters.mountainspirit.mountain.query.dto.CourseDTO;
import com.bughunters.mountainspirit.mountain.query.dto.MountainDTO;
import com.bughunters.mountainspirit.mountain.query.mapper.CourseGetMapper;
import com.bughunters.mountainspirit.mountain.query.mapper.MountainGetMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MountainSearchServiceImpl implements MountainSearchService{
    private MountainGetMapper mountainGetMapper;
    private CourseGetMapper courseGetMapper;

    private final SqlSessionTemplate sqlSession;

    @Autowired
    public MountainSearchServiceImpl(MountainGetMapper mountainGetMapper, CourseGetMapper courseGetMapper, SqlSessionTemplate sqlSession) {
        this.mountainGetMapper = mountainGetMapper;
        this.courseGetMapper = courseGetMapper;
        this.sqlSession = sqlSession;
    }

    @Override
    public List<MountainDTO> getAllMountainInfo() {
        List<MountainDTO> mountainList = mountainGetMapper.selectAllMountain();

        return mountainList;
    }

    @Override
    public List<MountainDTO> getSearchMountainInfo(String mountainName) {
        List<MountainDTO> mountainSearchList = mountainGetMapper.selectMountainByName(mountainName);
        return mountainSearchList;
    }

    @Override
    public List<CourseDTO> getAllCourseInfo() {
        List<CourseDTO> courseList = courseGetMapper.selectAllCourse();
        return courseList;
    }

    @Override
    public List<CourseDTO> getSearchCourseInfo(String mountainName) {
        List<CourseDTO> courseSearchList = courseGetMapper.selectCourseByName(mountainName);
        return courseSearchList;
    }

}
