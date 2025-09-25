package com.bughunters.mountainspirit.member.command.service;

import com.bughunters.mountainspirit.member.command.dto.FindClimbCheckDTO;
import com.bughunters.mountainspirit.member.command.dto.RequestStartClimbMountainDTO;
import com.bughunters.mountainspirit.member.command.dto.ResponseClimbMountainDTO;
import com.bughunters.mountainspirit.member.query.controller.MemberQueryController;

public interface MemberService {
    FindClimbCheckDTO startClimbMountain(RequestStartClimbMountainDTO request);
}
