package com.bughunters.mountainspirit.stamp.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CourseStampDTO {
    private long id;
    private java.sql.Date date;
    private String poiId;
    private long cumId;
}
