package com.bughunters.mountainspirit.report.command.controller;

import com.bughunters.mountainspirit.common.ResponseMessage;
import com.bughunters.mountainspirit.common.UserInfo;
import com.bughunters.mountainspirit.report.command.dto.BanAnnotationRequestDTO;
import com.bughunters.mountainspirit.report.command.entity.BanCommandEntity;
import com.bughunters.mountainspirit.report.command.service.BanCommandService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ban")
public class BanCommandController {
    private final BanCommandService banCommandService;

    @Autowired
    public BanCommandController(BanCommandService banCommandService) {
        this.banCommandService = banCommandService;
    }

    @PostMapping("/{banId}")
    public ResponseEntity<ResponseMessage> addAnnotation(
            @PathVariable Long banId,
            @RequestBody BanAnnotationRequestDTO dto,
            HttpServletRequest request
            ) {
        UserInfo userInfo = (UserInfo) request.getAttribute("userInfo");
        if(userInfo == null) {
            ResponseMessage response = new ResponseMessage(
                    HttpStatus.UNAUTHORIZED.value(),
                    "로그인이 필요합니다.",
                    Collections.emptyMap()
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        Long adminId = userInfo.getId();
        banCommandService.addAnnotation(banId, dto.getAnnotation(), adminId);

        BanCommandEntity updatedBan = banCommandService.getBanById(banId);

        Map<String, Object> data = new HashMap<>();
        data.put("banId", updatedBan.getId());
        data.put("annotation", updatedBan.getAnnotation());
        data.put("adminId", updatedBan.getAdminId());

        ResponseMessage response = new ResponseMessage(
                HttpStatus.OK.value(),
                "주석이 추가되었습니다.",
                data
        );

        return ResponseEntity.ok().body(response);

    }
}
