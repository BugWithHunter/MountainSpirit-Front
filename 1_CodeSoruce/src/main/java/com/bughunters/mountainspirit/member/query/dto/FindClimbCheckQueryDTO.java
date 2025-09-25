package com.bughunters.mountainspirit.member.query.dto;

import java.time.LocalDateTime;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FindClimbCheckQueryDTO {
    private String memberName;
    private LocalDateTime updateTime;
    private LocalDateTime endTime;
    private String frtrlNm;
    private String placeNm;
    private String stateCode;
}
