package com.bughunters.mountainspirit.admin.query.service;

import com.bughunters.mountainspirit.admin.query.dto.MemberInfoDTO;
import com.bughunters.mountainspirit.admin.query.dto.MemberInfoStatsDTO;
import com.bughunters.mountainspirit.admin.query.mapper.AdminMemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminMemberServiceImpl implements AdminMemberService {

    private AdminMemberMapper adminMemberMapper;
    @Autowired
    public AdminMemberServiceImpl(AdminMemberMapper adminMemberMapper) {
        this.adminMemberMapper = adminMemberMapper;
    }


    @Override
    public MemberInfoDTO getAllMembers(Integer page, Integer size) {
        int offset = (page - 1) * size;  // offset 계산
        List<Map<String, Object>> members = adminMemberMapper.selectAllMembers(size, offset);
        return new MemberInfoDTO(members);
    }

    @Override
    public MemberInfoStatsDTO getMemberStats() {
        int totalMembers = adminMemberMapper.selectTotalMembers();
        List<Map<String, Object>> genderRatio = adminMemberMapper.selectGenderRatio();
        List<Map<String, Object>> ageDistribution = adminMemberMapper.selectAgeDistribution();
        List<Map<String, Object>> monthlyNewMembers = adminMemberMapper.selectMonthlyNewMembers();

        return new MemberInfoStatsDTO(totalMembers, genderRatio, ageDistribution, monthlyNewMembers);
    }
}
