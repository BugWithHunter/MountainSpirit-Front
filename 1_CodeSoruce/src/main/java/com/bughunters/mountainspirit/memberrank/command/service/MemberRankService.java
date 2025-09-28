package com.bughunters.mountainspirit.memberrank.command.service;

import com.bughunters.mountainspirit.member.command.dto.RequestModifyStatusOfMemberDTO;
import com.bughunters.mountainspirit.memberrank.command.dto.RequestRankDTO;
import com.bughunters.mountainspirit.memberrank.command.dto.ResponseRankDTO;

public interface MemberRankService {
    ResponseRankDTO modifyMemberRank(RequestRankDTO rankDTO);

}
