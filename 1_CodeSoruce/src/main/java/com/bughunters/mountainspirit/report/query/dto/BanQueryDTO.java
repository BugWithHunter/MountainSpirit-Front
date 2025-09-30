package com.bughunters.mountainspirit.report.query.dto;

import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BanQueryDTO {
    private long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private long userId;
    private Long adminId; // nullable
}
