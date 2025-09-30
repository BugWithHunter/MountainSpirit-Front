package com.bughunters.mountainspirit.climbhistory.command.infrastructure;

import com.bughunters.mountainspirit.climbhistory.command.dto.RequestModifyStatusOfMemberDTO;
import com.bughunters.mountainspirit.climbhistory.command.dto.ResponseStatusDTO;
import com.bughunters.mountainspirit.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="member-client", url = "localhost:8000/member-client", configuration = FeignClientConfig.class)
public interface FeignClientClimbHisotryToMember {
    @GetMapping("member-client//{value}")
    String feignClientCon(@PathVariable String value);

    @PutMapping("/member-client/member/memberStatus")
    ResponseStatusDTO modifyStatusAfterClimbMountian(RequestModifyStatusOfMemberDTO modifyStatusOfMemberDTO);
}
