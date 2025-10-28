package com.bughunters.mountainspirit.admin.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CrewDetailSummaryDTO {
    private Long totalCrewMemberCount;         // 전체 크루 구성원 수
    private Long activeCrewMemberCount;        // 현재 활동 중인 크루 구성원 수
    private Long totalCrewClimbBoardCount;     // 전체 등반 게시글 수
    private Long totalCrewApplyCount;          // 전체 크루가입신청 수
    private Long totalCrewMemberHistoryCount;  // 전체 크루구성원 히스토리 수
}
