package com.bughunters.mountainspirit.member.query.dto;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestLoginwithAuthoritiesDTO {
    private Long id;
    private String email;
    private String memberName;
    private String birth;
    private Long memStsId;
    private String pwd;
    private List<Authority> authorities;


}
