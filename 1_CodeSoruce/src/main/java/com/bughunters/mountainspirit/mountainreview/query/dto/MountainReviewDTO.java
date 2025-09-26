package com.bughunters.mountainspirit.mountainreview.query.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MountainReviewDTO {
    private long id;
    private java.sql.Date registTime;
    private java.sql.Date updateTime;
    private String content;
    private long cumId;
    private String frtrlId;
}
