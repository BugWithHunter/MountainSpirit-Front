package com.bughunters.mountainspirit.report.query.controller;


import com.bughunters.mountainspirit.common.UserInfo;
import com.bughunters.mountainspirit.report.query.dto.ReportProtestQueryDTO;
import com.bughunters.mountainspirit.report.query.service.ReportProtestQueryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reportprotest")
public class ReportProtestQueryController {

    private ReportProtestQueryService reportProtestQueryService;

    @Autowired
    public ReportProtestQueryController(ReportProtestQueryService reportProtestQueryService) {
        this.reportProtestQueryService = reportProtestQueryService;
    }

    // 이의신청 전체조회
    @GetMapping("")
    public ResponseEntity<List<ReportProtestQueryDTO>> getAllReportProtest() {
        List<ReportProtestQueryDTO> getAllReportProtest = reportProtestQueryService.selectAllReportProtest();
        log.debug("ReportProtest={}", getAllReportProtest.size());
        return ResponseEntity.ok().body(getAllReportProtest);
    }

    // 로그인된 회원의 이의신청 조회
    @GetMapping("/my")
    public ResponseEntity<?> getMyReportProtest(HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");

        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Long reportedId = userInfo.getId();
        List<ReportProtestQueryDTO> protests = reportProtestQueryService.selectReportProtestByReportedId(reportedId);

        log.debug("reportedId={}, protestCount={}", reportedId, protests.size());
        return ResponseEntity.ok().body(protests);
    }
}
