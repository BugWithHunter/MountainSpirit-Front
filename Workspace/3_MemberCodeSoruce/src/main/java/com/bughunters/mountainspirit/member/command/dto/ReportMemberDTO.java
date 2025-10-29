package com.bughunters.mountainspirit.member.command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportMemberDTO {
    private Long id;
    private String memName;
    private String email;
    private Long memStsId;
    private int banCnt;
}
