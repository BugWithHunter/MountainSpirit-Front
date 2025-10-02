package com.bughunters.mountainspirit.mountainreview.query.service;

import com.bughunters.mountainspirit.mountainreview.query.dto.CourseReviewDTO;
import com.bughunters.mountainspirit.mountainreview.query.dto.MountainReviewDTO;
import com.bughunters.mountainspirit.mountainreview.query.mapper.CourseReviewGetMapper;
import com.bughunters.mountainspirit.mountainreview.query.mapper.MountainReviewGetMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MountainReviewServiceImpl implements MountainReviewService {
    private MountainReviewGetMapper mountainReviewGetMapper;
    private CourseReviewGetMapper courseReviewGetMapper;

    private final SqlSessionTemplate sqlSession;

    @Autowired
    public MountainReviewServiceImpl(MountainReviewGetMapper mountainReviewGetMapper, SqlSessionTemplate sqlSession
                                    , CourseReviewGetMapper courseReviewGetMapper) {
        this.mountainReviewGetMapper = mountainReviewGetMapper;
        this.courseReviewGetMapper = courseReviewGetMapper;
        this.sqlSession = sqlSession;
    }

    @Override
    public List<MountainReviewDTO> getMountainReview(String mountainName) {
        List<MountainReviewDTO> mountainReviewList = mountainReviewGetMapper.selectMountainReviewByName(mountainName);
        return mountainReviewList;
    }

    @Override
    public List<CourseReviewDTO> getCourseReview(String courseName) {
        List<CourseReviewDTO> courseReviewList = courseReviewGetMapper.selectCourseReviewByName(courseName);
        return courseReviewList;
    }
}
