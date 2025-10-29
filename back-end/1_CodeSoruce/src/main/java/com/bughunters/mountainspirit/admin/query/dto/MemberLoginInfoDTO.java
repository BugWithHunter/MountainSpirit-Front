package com.bughunters.mountainspirit.admin.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginInfoDTO {
    private Long id;
    private LocalDateTime lastLogin;
    private LocalDateTime quitDate;
    private LocalDateTime loginLockUntil;
    private Integer loginFailCnt;
    private String status;
}
