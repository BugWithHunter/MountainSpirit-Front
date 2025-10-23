package com.bughunters.mountainspirit.admin.query.mapper;

import com.bughunters.mountainspirit.admin.query.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMemberMapper {
    List<MemberInfoDTO> selectAllMembers(@Param("limit") int limit, @Param("offset") int offset);

    int selectTotalMembers();
    List<GenderRatioDTO> selectGenderRatio();
    List<AgeDistributionDTO> selectAgeDistribution();
    List<MonthlyNewMemberDTO> selectMonthlyNewMembers();

    List<MemberLoginInfoDTO> selectMemberLoginInfo(@Param("limit") int limit, @Param("offset") int offset);
    List<LoginStatsDTO> selectLoginStats();
    List<LoginTimeDistributionDTO> selectLoginTimeDistribution();
    List<MemberStatusRatioDTO> selectMemberStatusRatio();

    List<MemberRankInfoDTO> selectMemberRankInfo(@Param("limit") int limit, @Param("offset") int offset);
    List<MemberRankStatsDTO> selectMemberRankStats();



}
