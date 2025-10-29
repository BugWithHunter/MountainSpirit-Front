package com.bughunters.mountainspirit.mountain.query.service;

import com.bughunters.mountainspirit.mountain.command.entity.Course;
import com.bughunters.mountainspirit.mountain.command.entity.Mountain;
import com.bughunters.mountainspirit.mountain.command.repository.CourseRepository;
import com.bughunters.mountainspirit.mountain.command.repository.MountainRepository;
import com.bughunters.mountainspirit.mountain.query.dto.CourseDTO;
import com.bughunters.mountainspirit.mountain.query.dto.MountainDTO;
import com.bughunters.mountainspirit.mountain.query.mapper.CourseGetMapper;
import com.bughunters.mountainspirit.mountain.query.mapper.MountainGetMapper;
import org.modelmapper.ModelMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MountainSearchServiceImpl implements MountainSearchService{
    private MountainGetMapper mountainGetMapper;
    private CourseGetMapper courseGetMapper;
    private final MountainRepository mountainRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    private final SqlSessionTemplate sqlSession;

    @Autowired
    public MountainSearchServiceImpl(MountainGetMapper mountainGetMapper, CourseGetMapper courseGetMapper, MountainRepository mountainRepository, CourseRepository courseRepository, ModelMapper modelMapper, SqlSessionTemplate sqlSession) {
        this.mountainGetMapper = mountainGetMapper;
        this.courseGetMapper = courseGetMapper;
        this.mountainRepository = mountainRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
        this.sqlSession = sqlSession;
    }

    @Override
    public List<MountainDTO> getAllMountainInfo() {
        List<MountainDTO> mountainList = mountainGetMapper.selectAllMountain();

        return mountainList;
    }

    @Override
    public Page<MountainDTO> getAllMountainInfo(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(), Sort.by("id").descending());
        Page<Mountain> mountainList = mountainRepository.findAll(pageable);

        return mountainList.map(mountain -> modelMapper.map(mountain, MountainDTO.class));
    }

//    @Override
//    public Page<CourseDTO> getAllCourseInfo(Pageable pageable) {
//        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
//                pageable.getPageSize(), Sort.by("poiId").descending());
//        Page<Course> courseList = courseRepository.findAll(pageable);
//
//        return courseList.map(course -> modelMapper.map(course, CourseDTO.class));
//    }

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
