package com.bughunters.mountainspirit.report.command.controller;


import com.bughunters.mountainspirit.report.command.dto.ReportIsAccepted;
import com.bughunters.mountainspirit.report.command.dto.ReportRequestCommandDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportResponseCommandDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportStatusDTO;
import com.bughunters.mountainspirit.report.command.service.ReportCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class ReportCommandController {

    private final ReportCommandService reportCommandService;

    @Autowired
    public ReportCommandController(ReportCommandService reportCommandService){
        this.reportCommandService = reportCommandService;
    }

    @PostMapping("")
    public ResponseEntity<ReportResponseCommandDTO> createReport(
            @RequestBody ReportRequestCommandDTO reportRequestCommandDTO){
        ReportResponseCommandDTO reportResponseCommandDTO = reportCommandService.createReport(reportRequestCommandDTO);
        return ResponseEntity.ok().body(reportResponseCommandDTO);
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
