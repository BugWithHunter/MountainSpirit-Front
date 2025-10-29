package com.bughunters.mountainspirit.report.query.dto;

import lombok.Data;

@Data
public class ReportMemberDetailDTO {
    private Long reportId;          // 신고 ID
    private String isAccepted;      // 신고 상태 (Y/N/U)
    private String reason;          // 신고 사유
    private Long reportedId;        // 피신고자 ID
    private String memName;         // 회원 이름
    private Long crewId;            // 크루 ID
    private Integer climbCnt;       // 등산 횟수
    private Integer score;          // 점수
    private Integer reportCount;    // 해당 회원이 신고당한 횟수
}
