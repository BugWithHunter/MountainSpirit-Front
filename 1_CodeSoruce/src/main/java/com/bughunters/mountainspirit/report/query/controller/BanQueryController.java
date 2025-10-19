package com.bughunters.mountainspirit.report.query.controller;

import com.bughunters.mountainspirit.common.ResponseMessage;
import com.bughunters.mountainspirit.report.query.dto.BanQueryDTO;
import com.bughunters.mountainspirit.report.query.service.BanQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
    @Slf4j
    @RequestMapping("/bans")
    public class BanQueryController {

        private final BanQueryService banQueryService;

        @Autowired
        public BanQueryController(BanQueryService banQueryService) {
            this.banQueryService = banQueryService;
        }

        // 회원정지 전체조회
        // 관리자만 가능
        @GetMapping("")
        public ResponseEntity<ResponseMessage> getAllBan(
                @RequestParam(value = "page", defaultValue = "1") int page,
                @RequestParam(value = "size", defaultValue = "10") int size
        ) {
            List<BanQueryDTO> selectAllBan = banQueryService.selectAllBan(page, size);

            Map<String, Object> result = new HashMap<>();
            result.put("reports", selectAllBan);

            ResponseMessage responseMessage = new ResponseMessage(
                    HttpStatus.OK.value(),
                    "회원정지 전체 조회 성공",
                    result
            );

            log.debug("BanQuery size={}", selectAllBan.size());
            return ResponseEntity.ok().body(responseMessage);
        }
    }

