package com.bughunters.mountainspirit.admin.query.controller;

import com.bughunters.mountainspirit.admin.query.dto.MemberInfoDTO;
import com.bughunters.mountainspirit.admin.query.dto.MemberInfoStatsDTO;
import com.bughunters.mountainspirit.admin.query.service.AdminMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminMemberController {
    private final AdminMemberService adminMemberService;

    @Autowired
    public AdminMemberController(AdminMemberService adminMemberService) {
        this.adminMemberService = adminMemberService;
    }

    // 회원 목록 조회 (페이징)
    @GetMapping("/memberinfo")
    public ResponseEntity<MemberInfoDTO> getAllMembers(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        MemberInfoDTO result = adminMemberService.getAllMembers(page, size);
        return ResponseEntity.ok(result);
    }

    // 회원 통계 조회 (전체 기준)
    @GetMapping("/memberinfostats")
    public ResponseEntity<MemberInfoStatsDTO> getMemberStats() {
        MemberInfoStatsDTO result = adminMemberService.getMemberStats();
        return ResponseEntity.ok(result);
    }
}
