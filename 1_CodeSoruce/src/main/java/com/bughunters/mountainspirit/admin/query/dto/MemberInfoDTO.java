package com.bughunters.mountainspirit.admin.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoDTO {
    private Long id;
    private String memId;
    private String email;
    private String memName;
    private String nickname;
    private LocalDateTime birth;
    private String gender;
    private LocalDateTime signInDate;
}
