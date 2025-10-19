package com.bughunters.mountainspirit.report.command.controller;

import com.bughunters.mountainspirit.common.UserInfo;
import com.bughunters.mountainspirit.report.command.dto.ReportProtestRequestDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportProtestResponseDTO;

import com.bughunters.mountainspirit.report.command.dto.ReportProtestStatusUpdateDTO;
import com.bughunters.mountainspirit.report.command.service.ReportProtestCommandService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/protest")
public class ReportProtestCommandController {

    private final ReportProtestCommandService reportProtestCommandService;

    @Autowired
    public ReportProtestCommandController(ReportProtestCommandService reportProtestCommandService) {
        this.reportProtestCommandService = reportProtestCommandService;
    }

    @PostMapping("")
    public ResponseEntity<ReportProtestResponseDTO>  createReportProtest(@RequestBody ReportProtestRequestDTO dto) {
        ReportProtestResponseDTO saved = reportProtestCommandService.createReportProtest(dto);
        return ResponseEntity.ok().body(saved);
    }

    @PatchMapping("/{protestId}")
    public ResponseEntity<ReportProtestResponseDTO> updateProtestStatus(
            @PathVariable Long protestId,
            @RequestBody ReportProtestStatusUpdateDTO dto,
            HttpServletRequest request
    ) {
        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");

        if(userInfo == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long adminId = userInfo.getId();

        ReportProtestResponseDTO updated =
                reportProtestCommandService.updateProtestStatus(protestId, adminId, dto);

        return ResponseEntity.ok().body(updated);
    }


}
