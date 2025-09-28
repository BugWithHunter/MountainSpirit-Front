package com.bughunters.mountainspirit.mountainreview.command.dto;

import com.bughunters.mountainspirit.mountain.command.dto.MountainDTO;
import lombok.*;

import java.time.LocalDateTime;

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
