package com.bughunters.mountainspirit.admin.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MountainStatsDTO {
    Long totalMountain;
    List<MountainAllNumberDTO> mountainAllNumber;
    List<MountainTopDTO> mountainTop;
    List<ClimbAltitudeDTO> climbAltitude;
}
