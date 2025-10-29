package com.bughunters.mountainspirit.stamp.command.dto;

import com.bughunters.mountainspirit.memberrank.command.dto.ResponseMountainRankDTO;
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
