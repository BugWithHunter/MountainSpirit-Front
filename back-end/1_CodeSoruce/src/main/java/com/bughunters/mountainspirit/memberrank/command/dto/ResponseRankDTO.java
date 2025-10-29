package com.bughunters.mountainspirit.memberrank.command.dto;

import com.bughunters.mountainspirit.memberrank.command.entity.MemberRank;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseRankDTO {
    private boolean modifyMyMountainRank;
    private List<ResponseMountainRankDTO> modifyRanks;
    private List<MemberRank> baseMemberRanks;
    private int summaryScore;
}
