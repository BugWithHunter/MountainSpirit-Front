package com.bughunters.mountainspirit.report.query.controller;

import com.bughunters.mountainspirit.report.query.dto.BlacklistQueryDTO;
import com.bughunters.mountainspirit.report.query.service.BlacklistQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/blacklists")
public class BlacklistQueryController {

    private final BlacklistQueryService blacklistQueryService;

    @Autowired
    public BlacklistQueryController(BlacklistQueryService blacklistQueryService) {
        this.blacklistQueryService = blacklistQueryService;
    }

    // 블랙리스트 전체 조회
    // 관리자만 허용
    @GetMapping("")
    public ResponseEntity<List<BlacklistQueryDTO>> getAllBlacklist() {
        List<BlacklistQueryDTO> getAllBlacklist = blacklistQueryService.getAllBlacklist();
        log.debug("Blacklist size={}", getAllBlacklist.size());
        return ResponseEntity.ok().body(getAllBlacklist);
    }

}
