package com.bughunters.mountainspirit.report.command.controller;


import com.bughunters.mountainspirit.common.ResponseMessage;
import com.bughunters.mountainspirit.report.command.dto.ReportIsAccepted;
import com.bughunters.mountainspirit.report.command.dto.ReportRequestCommandDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportResponseCommandDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportStatusDTO;
import com.bughunters.mountainspirit.report.command.service.ReportCommandService;
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

    @PostMapping("")
    public ResponseEntity<ResponseMessage> createReport(
            @RequestBody ReportRequestCommandDTO reportRequestCommandDTO){
        ReportResponseCommandDTO reportResponseCommandDTO = reportCommandService.createReport(reportRequestCommandDTO);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("report", reportResponseCommandDTO);

        ResponseMessage response = new ResponseMessage(HttpStatus.OK.value(), "신고가 정상적으로 접수되었습니다.", responseMap);
        return ResponseEntity.ok(response);
    }

    // report테이블 update id는 report테이블의 pk
    @PatchMapping("/{id}")
    public ResponseEntity<ReportResponseCommandDTO> updateReportStatus(
            @PathVariable Long id,
            @RequestBody ReportStatusDTO statusDTO) {

        // 서비스 호출
        ReportResponseCommandDTO updatedReport =
                reportCommandService.updateReportStatus(id, ReportIsAccepted.valueOf(statusDTO.getStatus()));

        return ResponseEntity.ok().body(updatedReport);
    }
}
