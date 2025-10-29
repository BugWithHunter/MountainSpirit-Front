package com.bughunters.mountainspirit.climbhistory.command.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.bughunters.mountainspirit.memberrank.command.dto.ResponseMountainRankDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FindClimbCheckDTO {
    private LocalDateTime updateTime;
    private LocalDateTime endTime;

    private Long cumId;
    private String cumNm;
    private String frtrlId;
    private String poiId;
    private String frtrlNm;
    private String placeNm;
    private String stateCode;

    private boolean getMountainStamp;
    private boolean getCourseStamp;
    private int courseRank;
    private boolean modifyMyMountainRank;
    private long memRankId;
    private String memRankNm;

    private String mountainStampImagePath;
    private String courseStampImagePath;

    //합산 흭득 점수
    private int summaryScore;

    //산별 등급 객체 , 현재 회원 등급 정보, 산신령이 변경되면 이전 산신령 회원정보 포함
    private List<ResponseMountainRankDTO> modifyRanks;

}
