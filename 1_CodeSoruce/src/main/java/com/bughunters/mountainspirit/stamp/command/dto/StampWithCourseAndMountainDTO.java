package com.bughunters.mountainspirit.stamp.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StampWithCourseAndMountainDTO {
    private boolean newMountainStamp;
    private boolean newCourseStamp;
    private Long courseRank;

    //메모. 프론트 프로젝트에 추가 예정(스탬프 이미지 관련)
    private String mountainStampImagePath;
    private String courseStampImagePath;

}
