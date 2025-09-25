package com.bughunters.mountainspirit.member.command.controller;

import com.bughunters.mountainspirit.common.ResponseMessage;
import com.bughunters.mountainspirit.member.command.dto.FindClimbCheckDTO;
import com.bughunters.mountainspirit.member.command.dto.RequestStartClimbMountainDTO;
import com.bughunters.mountainspirit.member.command.dto.ResponseClimbMountainDTO;
import com.bughunters.mountainspirit.member.command.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //메모. 등산 시작 인증
//    @PostMapping("/members/{memberId}/mountain/{frtrlId}/course/{poiId}")
    @PostMapping("/members")
    public ResponseEntity<ResponseMessage> startClimbMountain(@RequestBody RequestStartClimbMountainDTO request) {
        FindClimbCheckDTO findClimbCheckDTO = memberService.startClimbMountain(request);
        String responseMessage = "";

        Map<String, Object> responseMap = new HashMap<>();
        if (findClimbCheckDTO != null) {
            responseMap.put("item", findClimbCheckDTO);
            responseMessage = "동일 코스로 30일 이내 완료 인증을 하지 않았습니다. \n정상에서 등산 인증 해 주세요.";
        } else {
            responseMessage = "등산 시작 인증 완료";
        }

        ResponseMessage responseMessageClass = new ResponseMessage(200,responseMessage , responseMap);
        return ResponseEntity.ok()
                .body(responseMessageClass);
    }

}
