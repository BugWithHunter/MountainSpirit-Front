package com.bughunters.mountainspirit.report.command.controller;

import com.bughunters.mountainspirit.common.ResponseMessage;
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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/protest")
public class ReportProtestCommandController {

    private final ReportProtestCommandService reportProtestCommandService;

    @Autowired
    public ReportProtestCommandController(ReportProtestCommandService reportProtestCommandService) {
        this.reportProtestCommandService = reportProtestCommandService;
    }

    @PostMapping("")
    public ResponseEntity<ResponseMessage>  createReportProtest(@RequestBody ReportProtestRequestDTO dto) {
        ReportProtestResponseDTO saved = reportProtestCommandService.createReportProtest(dto);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("protest", saved);

        ResponseMessage response = new ResponseMessage(
                HttpStatus.OK.value(),
                "이의제기가 정상적으로 등록되었습니다.",
                resultMap
        );

        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{protestId}")
    public ResponseEntity<ResponseMessage> updateProtestStatus(
            @PathVariable Long protestId,
            @RequestBody ReportProtestStatusUpdateDTO dto,
            HttpServletRequest request
    ) {
        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");

        if(userInfo == null){
            ResponseMessage errorResponse = new ResponseMessage(
                    HttpStatus.OK.value(),
                    "로그인 정보가 없습니다",
                    null
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        Long adminId = userInfo.getId();

        ReportProtestResponseDTO updated =
                reportProtestCommandService.updateProtestStatus(protestId, adminId, dto);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("protest", updated);

        ResponseMessage response = new ResponseMessage(
                HttpStatus.OK.value(),
                "이의제기 상태가 성공적으로 변경되었습니다.",
                resultMap
        );

        return ResponseEntity.ok().body(response);
    }


}
