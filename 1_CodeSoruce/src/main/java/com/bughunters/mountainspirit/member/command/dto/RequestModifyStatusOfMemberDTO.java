package com.bughunters.mountainspirit.member.command.dto;


import java.util.List;
import lombok.*;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestModifyStatusOfMemberDTO {
    private Long cumId;
    // key : 등급 기준 점수, value : primaryKey(id)
    private Map<Integer,Long> baseMemberRanks;
    private int summaryScore;
}
