package com.bughunters.mountainspirit.mountainbookmark.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CourseBookmarkDTO {
    private long id;
    private LocalDateTime bookmarkDate;
    private long cumId;
    private String poiId;
}
