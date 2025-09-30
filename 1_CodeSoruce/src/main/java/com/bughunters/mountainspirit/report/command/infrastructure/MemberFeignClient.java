package com.bughunters.mountainspirit.report.command.infrastructure;

import com.bughunters.mountainspirit.report.command.dto.ReportMemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-client" , url = "localhost:8080/member-client")
public interface MemberFeignClient {

    @GetMapping("/member/member-info/{id}")
    ReportMemberDTO getMemberInfo(@PathVariable("id") Long id);


}
