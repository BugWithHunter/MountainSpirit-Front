package com.bughunters.mountainspirit.member.command.dto;

import lombok.*;


@Setter
@NoArgsConstructor
@Getter
@AllArgsConstructor
@ToString
@Builder
public class ResponseQuitDTO {
    private boolean isQuit;
    private boolean isInvalidPwd;
}
