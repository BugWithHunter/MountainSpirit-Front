package com.bughunters.mountainspirit.report.query.controller;

import com.bughunters.mountainspirit.report.query.dto.BanQueryDTO;
import com.bughunters.mountainspirit.report.query.service.BanQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

    @RestController
    @Slf4j
    @RequestMapping("/bans")
    public class BanQueryController {

        private final BanQueryService banQueryService;

        @Autowired
        public BanQueryController(BanQueryService banQueryService) {
            this.banQueryService = banQueryService;
        }

        @GetMapping("")
        public ResponseEntity<List<BanQueryDTO>> getAllBan() {
            List<BanQueryDTO> selectAllBan = banQueryService.selectAllBan();
            log.debug("BanQuery size={}", selectAllBan.size());
            return ResponseEntity.ok().body(selectAllBan);
        }
    }

