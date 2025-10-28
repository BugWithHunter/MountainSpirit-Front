package com.bughunters.mountainspirit.report.query.controller;


import com.bughunters.mountainspirit.common.ResponseMessage;
import com.bughunters.mountainspirit.common.UserInfo;
import com.bughunters.mountainspirit.report.query.dto.ReportCheckDTO;
import com.bughunters.mountainspirit.report.query.dto.ReportDetailDTO;
import com.bughunters.mountainspirit.report.query.dto.ReportMemberDetailDTO;
import com.bughunters.mountainspirit.report.query.dto.ReportQueryDTO;
import com.bughunters.mountainspirit.report.query.service.ReportQueryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<ResponseMessage> selectReportsByType(
            @PathVariable String type,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        List<ReportCheckDTO> reports = reportQueryService.searchReportsByType(type,page,size);

        Map<String, Object> result = new HashMap<>();
        result.put("reports", reports);

        ResponseMessage responseMessage = new ResponseMessage(
                HttpStatus.OK.value(),
                "타입별 신고 조회 성공",
                result
        );

        return ResponseEntity.ok().body(responseMessage);
    }

    // 타입과 상태에 따른 신고 조회
    // 관리자 페이지에서 관리자만 사용가능
    @GetMapping("/type/{type}/status/{status}")
    public ResponseEntity<ResponseMessage> selectReportsByTypeAndStatus(
            @PathVariable String type,
            @PathVariable String status,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){
        List<ReportCheckDTO> reports = reportQueryService.selectReportsByTypeAndStatus(type, status, page, size);

        Map<String, Object> result = new HashMap<>();
        result.put("reports", reports);

        ResponseMessage responseMessage = new ResponseMessage(
          HttpStatus.OK.value(),
          "타입과 상태별 신고 조회 성공",
          result
        );

        return ResponseEntity.ok().body(responseMessage);
    }

    @GetMapping("/{id}")
    public List<ReportDetailDTO> getReportDetail(@PathVariable Long id) {
        return reportQueryService.selectReportDetailById(id);
    }

    @GetMapping("/member/{id}")
    public List<ReportMemberDetailDTO> getReportMemberDetail(@PathVariable Long id) {
        return reportQueryService.selectReportMemberDetailById(id);
    }

    // 로그인 된 회원 신고 조회: /reports/my
    // 마이페이지에서 사용자만 사용가능
    @GetMapping("/my/{memberId}")
    public ResponseEntity<ResponseMessage> selectReportsByMember(
            @PathVariable Long memberId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){

        List<ReportQueryDTO> reports = reportQueryService.selectReportsByMemberId(memberId, page, size);


        Map<String, Object> result = new HashMap<>();
        result.put("reports", reports);

        ResponseMessage responseMessage = new ResponseMessage(
                HttpStatus.OK.value(),
                "회원 신고 조회 성공",
                result
        );
        return ResponseEntity.ok().body(responseMessage);
    }

}
