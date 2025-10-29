package com.bughunters.mountainspirit.admin.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CourseStateDTO {
    private Long totalCourse;
    private List<CourseDifficultyStateDTO> courseDifficultyState;
    private List<CourseRegistMonthDTO> courseRegistMonth;
}
