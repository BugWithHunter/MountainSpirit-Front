package com.bughunters.mountainspirit.climbhistory.command.dto;

import com.bughunters.mountainspirit.memberrank.command.dto.ResponseMountainRankDTO;
import com.bughunters.mountainspirit.memberrank.command.entity.MemberRank;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseRankDTO {
    private List<ResponseMountainRankDTO> modifyRanks;
}
