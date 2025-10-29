package com.bughunters.mountainspirit.crewmember.command.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="MemberServiceClient",url="localhost:8000/member-client")
public interface MemberServiceClient {
    @GetMapping("/member/crew-insertion")
    String updateMemberCrewInfo(@RequestParam("crewId") long crewId,@RequestParam("cumId") long cumId);

    @GetMapping("/member/crew-quit")
    String deleteMemberCrewInfo(@RequestParam("crewId") long crewId,@RequestParam("cumId") long cumId);
}
