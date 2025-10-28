package com.bughunters.mountainspirit.climbhistory.query.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SpiritProfileDTO {
    private String frtrlId;
    private String frtrlNm;
    private Long cumId;
    private String filePath;
    private Double lat;
    private Double lot;

    List<MountainRankDTO> mountainRanks = new ArrayList<>();
}
