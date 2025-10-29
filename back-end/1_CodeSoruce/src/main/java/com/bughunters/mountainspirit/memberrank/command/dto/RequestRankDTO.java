package com.bughunters.mountainspirit.memberrank.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestRankDTO {
    private long cumId;
    private String poiId;
    private String frtrlId;

    //산 난이도 id
    private Long courseRank;
    //메모. 새로운 도장을 받았는지 확인 하는 변수
    private boolean newMountainStamp;
    private boolean newCourseStamp;
    //해당 산의 처음으로 등산 했을때 true : 산별 등급 insert 하기 위함
    private boolean firstClimbForMountain;

    //기존에 산도장이 있었는지 확인
    private boolean alreadyExistsMountainStamp;
}
