package com.bughunters.mountainspirit.member.command.dto;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RequestLoginwithAuthoritiesDTO {
    private Long id;
    private String email;
    private String memberName;
    private String birth;
    private Long memStsId;
    private String pwd;
    private List<Authority> authorities;
}
