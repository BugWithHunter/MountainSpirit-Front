package com.bughunters.mountainspirit.mountainreview.query.mapper;

import com.bughunters.mountainspirit.mountainreview.query.dto.CourseReviewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseReviewGetMapper {

    List<CourseReviewDTO> selectCourseReviewByName(String courseName);
}
