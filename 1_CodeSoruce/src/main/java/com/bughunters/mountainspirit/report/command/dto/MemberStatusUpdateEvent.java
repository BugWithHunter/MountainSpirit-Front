package com.bughunters.mountainspirit.report.command.dto;

public record MemberStatusUpdateEvent(Long memberId, Long memStsId, int banCnt) {
}
