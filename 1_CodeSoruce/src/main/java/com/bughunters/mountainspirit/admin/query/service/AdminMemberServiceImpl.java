package com.bughunters.mountainspirit.admin.query.service;

import com.bughunters.mountainspirit.admin.query.dto.*;
import com.bughunters.mountainspirit.admin.query.mapper.AdminMemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminMemberServiceImpl implements AdminMemberService {

    private AdminMemberMapper adminMemberMapper;
    @Autowired
    public AdminMemberServiceImpl(AdminMemberMapper adminMemberMapper) {
        this.adminMemberMapper = adminMemberMapper;
    }


    @Override
    public List<MemberInfoDTO> getAllMembers(Integer page, Integer size) {
        int offset = (page - 1) * size;  // offset 계산
        return adminMemberMapper.selectAllMembers(size, offset);


    }

    @Override
    public MemberInfoStatsDTO getMemberStats() {
        int totalMembers = adminMemberMapper.selectTotalMembers();
        List<GenderRatioDTO> genderRatio = adminMemberMapper.selectGenderRatio();
        List<AgeDistributionDTO> ageDistribution = adminMemberMapper.selectAgeDistribution();
        List<MonthlyNewMemberDTO> monthlyNewMembers = adminMemberMapper.selectMonthlyNewMembers();

        return new MemberInfoStatsDTO(totalMembers, genderRatio, ageDistribution, monthlyNewMembers);
    }

    @Override
    public List<MemberLoginInfoDTO> getMemberLoginInfo(Integer page, Integer size) {
        int offset = (page - 1) * size;
        return adminMemberMapper.selectMemberLoginInfo(size, offset);

    }

    @Override
    public MemberLoginInfoStatsDTO getMemberLoginStats() {
        List<LoginStatsDTO> loginStats = adminMemberMapper.selectLoginStats();
        List<LoginTimeDistributionDTO> loginTimeDistribution = adminMemberMapper.selectLoginTimeDistribution();
        List<MemberStatusRatioDTO>  memberStatusRatio = adminMemberMapper.selectMemberStatusRatio();
        return new MemberLoginInfoStatsDTO(loginStats, loginTimeDistribution, memberStatusRatio);
    }

    @Override
    public List<MemberRankInfoDTO> getMemberRankInfo(Integer page, Integer size) {
        int offset = (page - 1) * size;
        return adminMemberMapper.selectMemberRankInfo(size, offset);
    }

    @Override
    public List<MemberRankStatsDTO> getMemberRankStats() {
        return adminMemberMapper.selectMemberRankStats();
    }
}
