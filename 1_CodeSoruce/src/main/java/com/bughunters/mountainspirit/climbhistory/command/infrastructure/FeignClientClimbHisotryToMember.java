package com.bughunters.mountainspirit.climbhistory.command.infrastructure;

import com.bughunters.mountainspirit.climbhistory.command.dto.RequestModifyStatusOfMemberDTO;
import com.bughunters.mountainspirit.climbhistory.command.dto.ResponseStatusDTO;
import com.bughunters.mountainspirit.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="FeignClientClimbHisotryToMember", url = "localhost:8000/member-client", configuration = FeignClientConfig.class)
public interface FeignClientClimbHisotryToMember {
    @PutMapping("/member/memberStatus")
    ResponseStatusDTO modifyStatusAfterClimbMountian(@RequestBody RequestModifyStatusOfMemberDTO modifyStatusOfMemberDTO);
}
