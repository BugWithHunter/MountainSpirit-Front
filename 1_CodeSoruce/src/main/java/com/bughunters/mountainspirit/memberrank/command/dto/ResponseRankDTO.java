package com.bughunters.mountainspirit.memberrank.command.dto;

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
}
