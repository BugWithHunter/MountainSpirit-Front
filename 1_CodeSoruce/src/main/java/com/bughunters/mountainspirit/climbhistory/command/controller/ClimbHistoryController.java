package com.bughunters.mountainspirit.climbhistory.command.controller;

import com.bughunters.mountainspirit.common.ResponseMessage;
import com.bughunters.mountainspirit.climbhistory.command.dto.FindClimbCheckDTO;
import com.bughunters.mountainspirit.climbhistory.command.dto.RequestSubmmitClimbMountainDTO;
import com.bughunters.mountainspirit.climbhistory.command.service.ClimbHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/climb-history")
public class ClimbHistoryController {

    private final ClimbHistoryService climbHistoryService;

    public ClimbHistoryController(ClimbHistoryService climbHistoryService) {

        this.climbHistoryService = climbHistoryService;
    }

    //메모. 등산 시작 인증
    @PostMapping("/climbing")
    public ResponseEntity<ResponseMessage> startClimbMountain(@RequestBody RequestSubmmitClimbMountainDTO request) {
        FindClimbCheckDTO findClimbCheckDTO = climbHistoryService.startClimbMountain(request);
        String responseMessage = "";
        int statusCode = 0;


        Map<String, Object> responseMap = new HashMap<>();
        if (findClimbCheckDTO != null) {
            responseMap.put("item", findClimbCheckDTO);
            responseMessage = "동일 코스로 30일 이내 완료 인증을 하지 않았습니다. \n정상에서 등산 인증 해 주세요.";
            statusCode = 400;
        } else {
            responseMessage = "등산 시작 인증 완료";
            statusCode = 200;
        }

        ResponseMessage responseMessageClass = new ResponseMessage(statusCode,responseMessage , responseMap);
        return ResponseEntity.ok()
                .body(responseMessageClass);
    }


    //메모. 등산 완료 인증
    @PutMapping("/climbing")
    public ResponseEntity<ResponseMessage> completeClimbMountain(@RequestBody RequestSubmmitClimbMountainDTO request) {
        FindClimbCheckDTO findClimbCheckDTO = climbHistoryService.completeClimbMountain(request);
        String responseMessage = "";
        int statusCode = 0;


        Map<String, Object> responseMap = new HashMap<>();
        if (findClimbCheckDTO != null) {
            responseMap.put("member", findClimbCheckDTO);
            responseMessage = "봉우리 완등 인증 완료";
            statusCode = 200;
        } else {
            responseMessage = "현재 등산로에 등산 시작 정보가 없습니다.\n 등산완료 인증 실패";
            statusCode = 400;
        }

        ResponseMessage responseMessageClass = new ResponseMessage(statusCode,responseMessage , responseMap);
        return ResponseEntity.ok()
                .body(responseMessageClass);
    }

}
