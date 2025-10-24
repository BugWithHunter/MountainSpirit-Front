package com.bughunters.mountainspirit.admin.query.controller;

import com.bughunters.mountainspirit.admin.query.dto.*;
import com.bughunters.mountainspirit.admin.query.service.AdminMemberService;
import com.bughunters.mountainspirit.common.ResponseMessage;
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
@RequestMapping("/admin")
public class AdminMemberController {
    private final AdminMemberService adminMemberService;

    @Autowired
    public AdminMemberController(AdminMemberService adminMemberService) {
        this.adminMemberService = adminMemberService;
    }

    // 회원 목록 조회 (페이징)
    @GetMapping("/memberinfo")
    public ResponseEntity<ResponseMessage> getAllMembers(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        List<MemberInfoDTO> results = adminMemberService.getAllMembers(page, size);
        Map<String, Object> result = new HashMap<>();
        result.put("memberinfo", results);

        ResponseMessage responseMessage = new ResponseMessage(
                HttpStatus.OK.value(),
                "회원 조회 성공",
                result
        );

        return ResponseEntity.ok().body(responseMessage);
    }

    // 회원 통계 조회 (전체 기준)
    @GetMapping("/memberinfostats")
    public ResponseEntity<MemberInfoStatsDTO> getMemberStats() {
        MemberInfoStatsDTO result = adminMemberService.getMemberStats();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/memberlogin")
    public ResponseEntity<ResponseMessage> getMemberLoginInfo(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        List<MemberLoginInfoDTO> results = adminMemberService.getMemberLoginInfo(page, size);
        Map<String, Object> result = new HashMap<>();
        result.put("memberlogin", results);

        ResponseMessage responseMessage = new ResponseMessage(
                HttpStatus.OK.value(),
                "회원 조회 성공",
                result
        );

        return ResponseEntity.ok().body(responseMessage);
    }

    @GetMapping("/memberloginstats")
    public ResponseEntity<MemberLoginInfoStatsDTO> getMemberLoginStats() {
        MemberLoginInfoStatsDTO result = adminMemberService.getMemberLoginStats();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/memberrank")
    public ResponseEntity<ResponseMessage> getMemberRank(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ){
        List<MemberRankInfoDTO> results = adminMemberService.getMemberRankInfo(page, size);
        Map<String, Object> result = new HashMap<>();
        result.put("memberrank", results);

        ResponseMessage responseMessage = new ResponseMessage(
                HttpStatus.OK.value(),
                "회원 조회 성공",
                result
        );

        return ResponseEntity.ok().body(responseMessage);
    }

    @GetMapping("/memberrankstats")
    public ResponseEntity<List<MemberRankStatsDTO>> getMemberRankStats() {
        List<MemberRankStatsDTO> result = adminMemberService.getMemberRankStats();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/mountainstats")
    public ResponseEntity<MountainStatsDTO> getMountainStats(){
        MountainStatsDTO result = adminMemberService.getMountainStats();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/coursestats")
    public ResponseEntity<CourseStateDTO> getCourseStates(){
        CourseStateDTO result = adminMemberService.getCourseState();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/climbstats")
    public ResponseEntity<ClimbStateDTO> getClimbStates(){
        ClimbStateDTO result = adminMemberService.getClimbState();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/crewstats")
    public ResponseEntity<CrewStatsDTO> getCrewStats() {
        CrewStatsDTO result = adminMemberService.getCrewStatsSummary();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/crewmonthlystats")
    public ResponseEntity<List<CrewStatsDTO>> getCrewMonthlyStats() {
        List<CrewStatsDTO> result = adminMemberService.getCrewMonthlyStats();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/crewdetailstats")
    public AdminCrewStatsResponseDTO getCrewDetailStats() {
        return adminMemberService.getAllCrewStats();
    }


}
