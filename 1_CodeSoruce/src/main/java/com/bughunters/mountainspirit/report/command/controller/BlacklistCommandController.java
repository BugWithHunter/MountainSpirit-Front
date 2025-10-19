package com.bughunters.mountainspirit.report.command.controller;

import com.bughunters.mountainspirit.common.ResponseMessage;
import com.bughunters.mountainspirit.common.UserInfo;
import com.bughunters.mountainspirit.report.command.dto.BlacklistRequestDTO;
import com.bughunters.mountainspirit.report.command.service.BlacklistCommandService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/blacklist")
public class BlacklistCommandController {
    private final BlacklistCommandService blacklistCommandService;

    public BlacklistCommandController(BlacklistCommandService blacklistCommandService) {
        this.blacklistCommandService = blacklistCommandService;
    }

    @PostMapping("/comment")
    public ResponseEntity<ResponseMessage> addComment(
            @RequestBody BlacklistRequestDTO dto,
            HttpServletRequest request
    ) {
        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), "로그인이 필요합니다.", Collections.emptyMap()));
        }

        Long adminId = userInfo.getId();
        blacklistCommandService.addComment(dto, adminId);

        ResponseMessage response = new ResponseMessage(
                HttpStatus.OK.value(),
                "주석이 추가되었습니다.",
                Collections.emptyMap()
        );
        return ResponseEntity.ok(response);
    }
}
