package com.bughunters.mountainspirit.climbhistory.query.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseMountainRankDTO {
    private List<MountainRankDTO> mountainRank;
    private List<SpiritProfileDTO>  spiritProfileDTO;
}
