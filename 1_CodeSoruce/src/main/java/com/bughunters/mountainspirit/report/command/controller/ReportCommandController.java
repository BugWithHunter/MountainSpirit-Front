package com.bughunters.mountainspirit.report.command.controller;

import com.bughunters.mountainspirit.report.command.dto.ReportRequestCommandDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportResponseCommandDTO;
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

    @PostMapping("/report_create")
    public ResponseEntity<ReportResponseCommandDTO> createReport(@RequestBody ReportRequestCommandDTO reportRequestCommandDTO){
        ReportResponseCommandDTO reportResponseCommandDTO = reportCommandService.createReport(reportRequestCommandDTO);
        return ResponseEntity.ok().body(reportResponseCommandDTO);
    }
}
