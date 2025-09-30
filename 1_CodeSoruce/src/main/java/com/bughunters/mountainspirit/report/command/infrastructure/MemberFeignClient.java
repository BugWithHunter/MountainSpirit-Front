package com.bughunters.mountainspirit.report.command.infrastructure;

import com.bughunters.mountainspirit.report.command.dto.ReportMemberDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportMemberUpdateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "member-client" , url = "localhost:8080/member")
public interface MemberFeignClient {

    // 회원 조회
    @GetMapping("/report/member-info/{id}")
    ReportMemberDTO getMemberInfo(@PathVariable("id") Long id);

    // 회원 상태 업데이트 (banCnt, memStsId)
    @PatchMapping("/report/update-status/{id}")
    void updateMemberStatus(@PathVariable("id") Long id,
                            @RequestBody ReportMemberUpdateDTO dto);

}
