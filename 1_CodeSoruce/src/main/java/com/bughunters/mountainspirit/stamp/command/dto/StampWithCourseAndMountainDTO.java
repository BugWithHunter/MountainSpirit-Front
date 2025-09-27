package com.bughunters.mountainspirit.stamp.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StampWithCourseAndMountainDTO {
    private long cumId;
    private String poiId;
    private String frtrlId;

    private boolean newMountainStamp;
    private boolean newCourseStamp;
    private Long courseRank;
    //기존에 산도장이 있었는지 확인
    private boolean alreadyExistsMountainStamp;
    //해당 산의 처음으로 등산 했을때 true : 산별 등급 insert 하기 위함
    private boolean firstClimbForMountain;

    //메모. 프론트 프로젝트에 추가 예정(스탬프 이미지 관련)
    private String mountainStampImagePath;
    private String courseStampImagePath;

//    //MemberRank 패키지에서 온 반환 데이터
//    private ResponseRankDTO responseRankDTO;

}
