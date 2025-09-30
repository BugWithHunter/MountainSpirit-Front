package com.bughunters.mountainspirit.mountainreview.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MountainReviewDTO {
    private long id;
    private LocalDateTime registTime;
    private LocalDateTime updateTime;
    private String content;
    private long cumId;
    private String frtrlId;
}
