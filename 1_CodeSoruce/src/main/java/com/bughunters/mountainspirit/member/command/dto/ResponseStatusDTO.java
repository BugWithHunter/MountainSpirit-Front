package com.bughunters.mountainspirit.member.command.dto;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseStatusDTO {
    private int score;
    private boolean modifyMemberRank;
    private long memRankId;
    private String cumNm;
}
