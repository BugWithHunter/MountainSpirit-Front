package com.bughunters.mountainspirit.report.query.controller;


import com.bughunters.mountainspirit.report.query.dto.ReportQueryDTO;
import com.bughunters.mountainspirit.report.query.service.ReportQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("")
    public ResponseEntity<List<ReportQueryDTO>> getReports(
            @RequestParam(value = "type", required = false) String reportType,
            @RequestParam(value = "status", required = false) String isAccepted
    ){
        if (reportType == null && isAccepted == null){
            List<ReportQueryDTO> reportAll = reportQueryService.selectAllReport();
            log.debug("reportAll size={}", reportAll.size());
            return ResponseEntity.ok().body(reportAll);
        } else {
            // 조건 조회: /reports?type=crew&status=N
            List<ReportQueryDTO> selectReports = reportQueryService.selectReportsByTypeAndStatus(reportType, isAccepted);
            log.debug("searchReports result size={}", selectReports.size());
            return ResponseEntity.ok().body(selectReports);
        }

    }

}
