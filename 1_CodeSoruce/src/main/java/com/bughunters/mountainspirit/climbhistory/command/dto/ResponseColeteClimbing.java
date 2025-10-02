package com.bughunters.mountainspirit.climbhistory.command.dto;

import com.bughunters.mountainspirit.climbhistory.query.dto.FindClimbCheckQueryDTO;
import com.bughunters.mountainspirit.memberrank.command.dto.ResponseRankDTO;
import com.bughunters.mountainspirit.stamp.command.dto.StampWithCourseAndMountainDTO;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseColeteClimbing {
    FindClimbCheckQueryDTO findClimbCheckQueryDTO;
    StampWithCourseAndMountainDTO stampDTO;
    ResponseRankDTO responseRankDTO;
}
