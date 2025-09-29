package com.bughunters.mountainspirit.member.command.controller;

import com.bughunters.mountainspirit.common.ResponseMessage;
import com.bughunters.mountainspirit.member.command.dto.RequestMemberDTO;
import com.bughunters.mountainspirit.member.command.dto.RequestQuitMemberDTO;
import com.bughunters.mountainspirit.member.command.dto.ResponseQuitDTO;
import com.bughunters.mountainspirit.member.command.dto.ResponseSignUpDTO;
import com.bughunters.mountainspirit.member.command.entity.Member;
import com.bughunters.mountainspirit.member.command.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberController(MemberService memberService
    , BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberService = memberService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/member-info/{id}")
    public Member memberTest(@PathVariable Long id) {

        Member member = memberService.getTest(id);

        return member;
    }

    @PostMapping("/member")
    public ResponseEntity<ResponseMessage> addMember(@RequestBody RequestMemberDTO member) {

        try {
            // BCrypt 암호화
            member.setMemPwd(bCryptPasswordEncoder.encode(member.getMemPwd()));
            ResponseSignUpDTO memberSignUpDTO = memberService.signUp(member);
            Map<String, Object> responseMap = new HashMap<>();
            ResponseMessage responseMessage = new ResponseMessage();

            if (memberSignUpDTO.isBanMember()) {
                responseMessage.setMessage(member.getMemName() + " 회원님은 가입이 제한된 회원 입니다.");
                responseMap.put("memberInfo", memberSignUpDTO.getBlackListDTO());
            } else if (memberSignUpDTO.isDuplicateEmail()) {
                responseMessage.setMessage(member.getEmail() + " 이미 존재하는 이메일입니다. 이메일 주소를 확인해 주세요");
            } else if (memberSignUpDTO.isExistingMember()) {
                responseMessage.setMessage(member.getMemName() + " 님은 이미 존재하는 회원 정보입니다.");
            } else {
                responseMap.put("회원 정보", memberSignUpDTO.getMemberDTO());
                responseMessage.setMessage("회원가입이 완료 되었습니다.");
            }

            if (!responseMap.isEmpty())
                responseMessage.setResult(responseMap);

            responseMessage.setHttpStatus(HttpStatus.OK.value());
            return ResponseEntity.ok()
                    .body(responseMessage);
        } catch (IllegalArgumentException e) {
            ResponseMessage responseMessage =
                    new ResponseMessage(HttpStatus.BAD_REQUEST.value(), "필수 입력 정보가 누락 됐습니다.", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(responseMessage);
        }
    }

    @DeleteMapping("/member")
    public ResponseEntity<ResponseMessage> deleteMember(@RequestBody RequestQuitMemberDTO member) {
        ResponseQuitDTO responseQuitDTO = memberService.memberQuit(member);
        ResponseMessage responseMessage = new ResponseMessage();

        if (responseQuitDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (responseQuitDTO.isInvalidPwd()) {
            responseMessage.setMessage("암호가 틀렸습니다.");
        } else {
            responseMessage.setMessage("회원 탈퇴가 완료 됐습니다.");
        }

        responseMessage.setHttpStatus(HttpStatus.OK.value());

        return ResponseEntity.ok()
                .body(responseMessage);
    }
}
