package com.bughunters.mountainspirit.stamp.query.mapper;

import com.bughunters.mountainspirit.stamp.query.dto.CourseStampDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseStampGetMapper {
    List<CourseStampDTO> selectCourseStampById(long memberId);
}
