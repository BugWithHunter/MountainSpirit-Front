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
        Long totalMembers = adminMemberMapper.selectTotalMembers();
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

    @Override
    public MountainStatsDTO getMountainStats() {
        Long totalMountain = adminMemberMapper.selectTotalMountain();
        List<MountainAllNumberDTO> mountainAllNumber = adminMemberMapper.selectAllMountainNumber();
        List<MountainTopDTO> mountainTop = adminMemberMapper.selectNumberOfClimbsTop5();
        List<ClimbAltitudeDTO> climbAltitude = adminMemberMapper.selectNumberOfClimbAltitude();
        return new MountainStatsDTO(totalMountain, mountainAllNumber,mountainTop, climbAltitude);
    }

    @Override
    public CourseStateDTO getCourseState() {
        Long totalCourse = adminMemberMapper.selectTotalCourse();
        List<CourseDifficultyStateDTO>  courseDifficultyState = adminMemberMapper.selectCourseDifficultyState();
        List<CourseRegistMonthDTO> courseRegistMonth = adminMemberMapper.selectCourseRegistMonth();
        return new CourseStateDTO(totalCourse, courseDifficultyState, courseRegistMonth);
    }

    @Override
    public ClimbStateDTO getClimbState() {
        List<TotalStampBookmarkDTO> totalStampBookmark = adminMemberMapper.selectTotalStampBookmark();
        List<ClimbStartDTO> climbStartState = adminMemberMapper.selectClimbStartState();
        List<ClimbEndDTO> climbEndState = adminMemberMapper.selectClimbEndState();
        return new ClimbStateDTO(totalStampBookmark, climbStartState, climbEndState);
    }

    @Override
    public CrewStatsDTO getCrewStatsSummary() {

        int totalCrewCount = adminMemberMapper.selectTotalCrewCount().getTotalCrewCount();
        int newCrewCount = adminMemberMapper.selectNewCrewThisMonth().getNewCrewCount();
        int activeCrewCount = adminMemberMapper.selectActiveCrewCount().getActiveCrewCount();
        List<CrewMonthlyStatsDTO> monthlyCrewStats = adminMemberMapper.selectMonthlyCrewStats();

        return new CrewStatsDTO(totalCrewCount, newCrewCount, activeCrewCount, monthlyCrewStats);
    }


    @Override
    public AdminCrewStatsResponseDTO getAllCrewStats() {
        AdminCrewStatsResponseDTO response = new AdminCrewStatsResponseDTO();

        // 각각 Mapper 호출
        CrewDetailSummaryDTO countSummary = adminMemberMapper.selectAllCountSummary();
        AdminMemberAffiliationDTO memberAffiliation = adminMemberMapper.selectMemberAffiliationStats();
        AdminCrewMemberStateRateDTO memberStateRates = adminMemberMapper.selectCrewMemberStateRates();
        AdminCrewClimbSuccessDTO climbSuccessRate = adminMemberMapper.selectCrewClimbSuccessRate();

        // DTO에 세팅
        response.setCountSummary(countSummary);
        response.setMemberAffiliation(memberAffiliation);
        response.setMemberStateRates(memberStateRates);
        response.setClimbSuccessRate(climbSuccessRate);

        return response;
    }


}
