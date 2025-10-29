package com.bughunters.mountainspirit.report.command.controller;

import com.bughunters.mountainspirit.common.ResponseMessage;
import com.bughunters.mountainspirit.common.UserInfo;
import com.bughunters.mountainspirit.report.command.dto.BlacklistRequestDTO;
import com.bughunters.mountainspirit.report.command.entity.BlacklistCommandEntity;
import com.bughunters.mountainspirit.report.command.service.BlacklistCommandService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/blacklist")
public class BlacklistCommandController {
    private final BlacklistCommandService blacklistCommandService;

    public BlacklistCommandController(BlacklistCommandService blacklistCommandService) {
        this.blacklistCommandService = blacklistCommandService;
    }

    @PostMapping("/{blacklistId}")
    public ResponseEntity<ResponseMessage> addComment(
            @PathVariable Long blacklistId,
            @RequestBody BlacklistRequestDTO dto,
            HttpServletRequest request
    ) {
        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        if (userInfo == null) {
            ResponseMessage errorResponse = new ResponseMessage(
                    HttpStatus.UNAUTHORIZED.value(),
                    "로그인이 필요합니다",
                    Collections.emptyMap()
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        Long adminId = userInfo.getId();
        try {
                    blacklistCommandService.addComment(blacklistId, dto, adminId);
        } catch (IllegalArgumentException e) {
            ResponseMessage errorResponse = new ResponseMessage(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    Collections.emptyMap()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        ResponseMessage response = new ResponseMessage(
                HttpStatus.OK.value(),
                "주석이 추가되었습니다.",
                Collections.emptyMap()
        );
        return ResponseEntity.ok(response);
    }
}
