package com.bughunters.mountainspirit.admin.query.mapper;

import com.bughunters.mountainspirit.admin.query.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

@Mapper
public interface AdminMemberMapper {
    List<MemberInfoDTO> selectAllMembers(@Param("limit") int limit, @Param("offset") int offset);

    Long selectTotalMembers();
    List<GenderRatioDTO> selectGenderRatio();
    List<AgeDistributionDTO> selectAgeDistribution();
    List<MonthlyNewMemberDTO> selectMonthlyNewMembers();

    List<MemberLoginInfoDTO> selectMemberLoginInfo(@Param("limit") int limit, @Param("offset") int offset);
    List<LoginStatsDTO> selectLoginStats();
    List<LoginTimeDistributionDTO> selectLoginTimeDistribution();
    List<MemberStatusRatioDTO> selectMemberStatusRatio();

    List<MemberRankInfoDTO> selectMemberRankInfo(@Param("limit") int limit, @Param("offset") int offset);
    List<MemberRankStatsDTO> selectMemberRankStats();


    Long selectTotalMountain();
    List<MountainAllNumberDTO> selectAllMountainNumber();
    List<MountainTopDTO> selectNumberOfClimbsTop5();
    List<ClimbAltitudeDTO> selectNumberOfClimbAltitude();

    Long selectTotalCourse();
    List<CourseDifficultyStateDTO> selectCourseDifficultyState();
    List<CourseRegistMonthDTO> selectCourseRegistMonth();

    List<TotalStampBookmarkDTO> selectTotalStampBookmark();
    List<ClimbStartDTO> selectClimbStartState();
    List<ClimbEndDTO>  selectClimbEndState();

    // 전체 크루 수
    CrewStatsDTO selectTotalCrewCount();
    // 이번 달 새로 생성된 크루 수
    CrewStatsDTO selectNewCrewThisMonth();
    // 월별 생성 통계
    List<CrewMonthlyStatsDTO> selectMonthlyCrewStats();
    // 활동 중인 크루 수
    CrewStatsDTO selectActiveCrewCount();

    CrewDetailSummaryDTO selectAllCountSummary();
    AdminMemberAffiliationDTO selectMemberAffiliationStats();
    AdminCrewMemberStateRateDTO selectCrewMemberStateRates();
    AdminCrewClimbSuccessDTO selectCrewClimbSuccessRate();
}

