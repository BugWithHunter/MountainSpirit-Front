package com.bughunters.mountainspirit.report.command.controller;


import com.bughunters.mountainspirit.common.ResponseMessage;
import com.bughunters.mountainspirit.common.UserInfo;
import com.bughunters.mountainspirit.report.command.dto.ReportIsAccepted;
import com.bughunters.mountainspirit.report.command.dto.ReportRequestCommandDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportResponseCommandDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportStatusDTO;
import com.bughunters.mountainspirit.report.command.service.ReportCommandService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportCommandController {

    private final ReportCommandService reportCommandService;

    @Autowired
    public ReportCommandController(ReportCommandService reportCommandService){
        this.reportCommandService = reportCommandService;
    }

    // 신고 생성 사용자와 관리자 모두 가능
    @PostMapping("")
    public ResponseEntity<ResponseMessage> createReport(
            @RequestBody ReportRequestCommandDTO reportRequestCommandDTO){
        ReportResponseCommandDTO reportResponseCommandDTO = reportCommandService.createReport(reportRequestCommandDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("report", reportResponseCommandDTO);

        ResponseMessage response = new ResponseMessage(HttpStatus.OK.value(), "신고가 정상적으로 접수되었습니다.", responseMap);
        return ResponseEntity.ok(response);
    }

    // 신고에 대한 상태변경
    // report테이블 update, id는 report테이블의 pk
    // 관리자만 가능
    @PatchMapping("/{id}")
    public ResponseEntity<ReportResponseCommandDTO> updateReportStatus(
            @PathVariable Long id,
            @RequestBody ReportStatusDTO statusDTO,
            HttpServletRequest request) {

        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");

        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Long adminId = userInfo.getId();
        // 서비스 호출
        ReportResponseCommandDTO updatedReport =
                reportCommandService.updateReportStatus(id,
                        ReportIsAccepted.valueOf(statusDTO.getStatus()),
                        adminId
                        );

        return ResponseEntity.ok().body(updatedReport);
    }
}
