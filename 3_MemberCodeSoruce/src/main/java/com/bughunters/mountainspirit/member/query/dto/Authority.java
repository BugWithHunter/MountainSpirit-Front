package com.bughunters.mountainspirit.member.query.dto;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Authority {
    private Long cumId;
    private Long authId;
    private String authName;
    private String authDescribe;
}
