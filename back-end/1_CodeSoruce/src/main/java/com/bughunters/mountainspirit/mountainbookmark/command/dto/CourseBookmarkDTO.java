package com.bughunters.mountainspirit.mountainbookmark.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CourseBookmarkDTO {
    private long id;
    private java.sql.Date bookmarkDate;
    private long cumId;
    private String poiId;
}
