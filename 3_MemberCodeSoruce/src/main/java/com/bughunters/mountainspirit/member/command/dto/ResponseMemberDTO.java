package com.bughunters.mountainspirit.member.command.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ResponseMemberDTO {

    private Long id;
    private String memId;
    private String email;
    private String nickname;
    private String memName;
    private LocalDate birth;
    private String gender;
    private LocalDate signInDate;
}