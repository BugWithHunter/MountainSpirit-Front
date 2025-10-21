package com.bughunters.mountainspirit.admin.query.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMemberMapper {
    List<Map<String, Object>> selectAllMembers(int limit, int offset);
    int selectTotalMembers();
    List<Map<String, Object>> selectGenderRatio();
    List<Map<String, Object>> selectAgeDistribution();
    List<Map<String, Object>> selectMonthlyNewMembers();
}
