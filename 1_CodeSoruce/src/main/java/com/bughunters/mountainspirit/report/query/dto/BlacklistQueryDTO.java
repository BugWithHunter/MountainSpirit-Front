package com.bughunters.mountainspirit.report.query.dto;

import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BlacklistQueryDTO {
    private long id;
    private LocalDateTime createDate;
    private Long adminId;   // nullable
    private long memberId;  // not null
}
