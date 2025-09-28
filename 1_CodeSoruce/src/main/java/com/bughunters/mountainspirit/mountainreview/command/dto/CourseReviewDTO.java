package com.bughunters.mountainspirit.mountainreview.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CourseReviewDTO {
    private long id;
    private java.sql.Date commitDate;
    private java.sql.Date updateDate;
    private String reviewContent;
    private String poiId;
    private long cumId;
}
