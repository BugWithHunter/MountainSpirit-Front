package com.bughunters.mountainspirit.report.query.controller;


import com.bughunters.mountainspirit.common.ResponseMessage;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<ResponseMessage> getAllReportProtest(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        List<ReportProtestQueryDTO> getAllReportProtest = reportProtestQueryService.selectAllReportProtest(page, size);

        Map<String, Object> result = new HashMap<>();
        result.put("reports", getAllReportProtest);

        ResponseMessage responseMessage = new ResponseMessage(
                HttpStatus.OK.value(),
                "이의신청 전체 조회 성공",
                result
        );

        log.debug("ReportProtest={}", getAllReportProtest.size());
        return ResponseEntity.ok().body(responseMessage);
    }

    // 로그인된 회원의 이의신청 조회
    @GetMapping("/my")
    public ResponseEntity<ResponseMessage> getMyReportProtest(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");

        if (userInfo == null) {
            ResponseMessage unauthorizedMessage = new ResponseMessage(
                    HttpStatus.UNAUTHORIZED.value(),
                    "로그인이 필요합니다.",
                    null
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(unauthorizedMessage);
        }

        Long reportedId = userInfo.getId();
        List<ReportProtestQueryDTO> protests = reportProtestQueryService.selectReportProtestByReportedId(reportedId, page, size);

        Map<String, Object> result = new HashMap<>();
        result.put("reports", protests);

        ResponseMessage responseMessage = new ResponseMessage(
                HttpStatus.OK.value(),
                "나의 이의신청 조회 성공",
                result
        );

        log.debug("reportedId={}, protestCount={}", reportedId, protests.size());
        return ResponseEntity.ok().body(responseMessage);
    }
}
