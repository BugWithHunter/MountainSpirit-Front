package com.bughunters.mountainspirit.report.command.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReportProtestResponseDTO {
    private Long id;
    private LocalDateTime appealDate;
    private String adminId;
    private String reason;
    private String isAccepted;
    private Long reportedId;
    private Long reportId;
}
