package com.bughunters.mountainspirit.report.command.infrastructure;

import com.bughunters.mountainspirit.report.command.dto.ReportMemberDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportMemberUpdateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "feignMemberFeignClient" , url = "localhost:8000/member-client")
public interface MemberFeignClient {

    // 회원 조회
    @GetMapping("/member/report/member-info/{id}")
    ReportMemberDTO getMemberInfo(@PathVariable("id") Long id);

    // 회원 상태 업데이트 (banCnt, memStsId)
    @PostMapping("/member/report/update-status/{memberId}")
    void updateMemberStatus(@PathVariable("memberId") Long memberId,
                            @RequestBody ReportMemberUpdateDTO dto);

}
