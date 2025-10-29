package com.bughunters.mountainspirit.mountainreview.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CourseReviewDTO {
    private long id;
    private LocalDateTime commitDate;
    private LocalDateTime updateDate;
    private String reviewContent;
    private String poiId;
    private long cumId;
}
