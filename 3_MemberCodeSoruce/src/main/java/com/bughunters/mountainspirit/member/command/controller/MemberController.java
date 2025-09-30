package com.bughunters.mountainspirit.member.command.controller;

import com.bughunters.mountainspirit.common.ResponseMessage;
import com.bughunters.mountainspirit.member.command.dto.*;
import com.bughunters.mountainspirit.member.command.entity.Member;
import com.bughunters.mountainspirit.member.command.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberController(MemberService memberService
    , BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberService = memberService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public String test(HttpServletRequest request, HttpServletResponse response) {


        return"";
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

    @PutMapping("memberStatus")
    ResponseStatusDTO modifyStatusAfterClimbMountian(
            @RequestBody RequestModifyStatusOfMemberDTO modifyStatusOfMemberDTO) {
        System.out.println("modifyStatusOfMemberDTO = " + modifyStatusOfMemberDTO);
        System.out.println("modifyStatusAfterClimbMountian 들어옴 ");
        ResponseStatusDTO responseStatusDTO = memberService.modifyStatusAfterClimbMountian(modifyStatusOfMemberDTO);
        System.out.println("modifyStatusAfterClimbMountian 서비스까지 실행함 ");
        return responseStatusDTO;
    }

    @GetMapping("/crew-insertion")
    public ResponseEntity<ResponseMessage> crewInfoInsertion(long crewId,long cumId) {
        ResponseMessage responseMessage = new ResponseMessage();
        log.info("넘어온 크루,유저 아이디 : {}, {}",crewId,cumId);
        memberService.registCrewId(crewId,cumId);

        responseMessage.setHttpStatus(HttpStatus.OK.value());
        return ResponseEntity.ok()
                .body(responseMessage);
    }

    @GetMapping("/report/member-info/{id}")
    public ResponseEntity<ReportMemberDTO> getMemberInfo(@PathVariable Long id) {
        Member member = memberService.getTest(id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ReportMemberDTO dto = new ReportMemberDTO(
                member.getId(),
                member.getMemName(),
                member.getEmail(),
                member.getMemStsId(),
                member.getBanCnt() != null ? member.getBanCnt() : 0
        );

        return ResponseEntity.ok().body(dto);
    }

    @PatchMapping("/report/update-status/{id}")
    public ResponseEntity<Void> updateMemberStatus(@PathVariable Long id,
                                                   @RequestBody ReportMemberUpdateDTO dto) {
        boolean updated = memberService.updateStatus(id, dto);
        if (!updated) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }



}
