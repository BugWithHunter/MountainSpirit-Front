package com.bughunters.mountainspirit.mountain.query.mapper;

import com.bughunters.mountainspirit.mountain.query.dto.CourseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseGetMapper {

    List<CourseDTO> selectAllCourse();

    List<CourseDTO> selectCourseByName(String mountainName);
}
