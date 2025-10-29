package com.bughunters.mountainspirit.report.query.controller;

import com.bughunters.mountainspirit.common.ResponseMessage;
import com.bughunters.mountainspirit.report.query.dto.BlacklistByIdDTO;
import com.bughunters.mountainspirit.report.query.dto.BlacklistQueryDTO;
import com.bughunters.mountainspirit.report.query.service.BlacklistQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<ResponseMessage> getAllBlacklist(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        List<BlacklistQueryDTO> getAllBlacklist = blacklistQueryService.getAllBlacklist(page, size);

        Map<String, Object> result = new HashMap<>();
        result.put("reports", getAllBlacklist);

        ResponseMessage responseMessage = new ResponseMessage(
                HttpStatus.OK.value(),
                "블랙리스트 전체 조회 성공",
                result
        );

        log.debug("Blacklist size={}", getAllBlacklist.size());
        return ResponseEntity.ok().body(responseMessage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getBlacklistById(
        @PathVariable Long id
    ){
        List<BlacklistByIdDTO> getBlacklistById = blacklistQueryService.getBlacklistById(id);

        Map<String, Object> result = new HashMap<>();
        result.put("blacklistById", getBlacklistById);
        ResponseMessage responseMessage = new ResponseMessage(
                HttpStatus.OK.value(),
                "블랙리스트 아이디 조회 성공",
                result
        );
        log.debug("BlacklistById getBlacklistById={}", getBlacklistById);
        return ResponseEntity.ok().body(responseMessage);
    }

}
