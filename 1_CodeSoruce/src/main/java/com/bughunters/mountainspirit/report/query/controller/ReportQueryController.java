package com.bughunters.mountainspirit.report.query.controller;


import com.bughunters.mountainspirit.common.UserInfo;
import com.bughunters.mountainspirit.report.query.dto.ReportQueryDTO;
import com.bughunters.mountainspirit.report.query.service.ReportQueryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/reports")
public class ReportQueryController {
    private final ReportQueryService reportQueryService;

    @Autowired
    public ReportQueryController(ReportQueryService reportQueryService) {
        this.reportQueryService = reportQueryService;
    }

    // 타입과 따른 신고 조회
    // 관리자 페이지에서 관리자만 사용가능
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ReportQueryDTO>> selectReportsByTypeAndStatus(@PathVariable String type) {
        List<ReportQueryDTO> reports = reportQueryService.searchReportsByType(type);
        return ResponseEntity.ok().body(reports);
    }

    // 타입과 상태에 따른 신고 조회
    // 관리자 페이지에서 관리자만 사용가능
    @GetMapping("/type/{type}/status/{status}")
    public ResponseEntity<List<ReportQueryDTO>> getReportsByTypeAndStatus(
            @PathVariable String type,
            @PathVariable String status,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){
        List<ReportQueryDTO> reports = reportQueryService.selectReportsByTypeAndStatus(type, status, page, size);
        return ResponseEntity.ok().body(reports);
    }

    // 로그인 된 회원 신고 조회: /reports/my
    // 마이페이지에서 사용자만 사용가능
    @GetMapping("/my")
    public ResponseEntity<?> getReportsByMember(
            HttpServletRequest request
    ){

        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");

        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");
        }

        Long memberId = userInfo.getId();

        List<ReportQueryDTO> reports = reportQueryService.selectReportsByMemberId(memberId);
        log.debug("memberId={} reports size={}", memberId, reports.size());
        return ResponseEntity.ok().body(reports);
    }

}
