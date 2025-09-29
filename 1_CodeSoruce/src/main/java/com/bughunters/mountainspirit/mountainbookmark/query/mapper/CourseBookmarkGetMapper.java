package com.bughunters.mountainspirit.mountainbookmark.query.mapper;

import com.bughunters.mountainspirit.mountainbookmark.query.dto.CourseBookmarkDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseBookmarkGetMapper {
    List<CourseBookmarkDTO> selectCourseBookmarkById(Long memberId);
}
