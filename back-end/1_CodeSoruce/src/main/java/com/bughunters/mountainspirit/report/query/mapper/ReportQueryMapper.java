package com.bughunters.mountainspirit.report.query.mapper;

import com.bughunters.mountainspirit.report.query.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportQueryMapper {
    List<ReportQueryDTO> reportAll(
            @Param("limit") Integer limit,
            @Param("offset") Integer offset
    );

    List<ReportCheckDTO> findReportsByType(
            @Param("reportType") String reportType,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset);


    // 신고 타입과 신고 상태에 따른 페이징 조회
    List<ReportCheckDTO> findReportsByTypeAndStatus(
            @Param("reportType") String reportType,
            @Param("isAccepted") String isAccepted,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset
    );

    List<ReportDetailDTO> findReportDetailById(@Param("id") Long id);

    // 회원 단건 신고 조회
    List<ReportQueryDTO> findReportsByMemberId(
            @Param("memberId") Long memberId,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset);

    // 회원신고 클릭시 회원정보와 해당 신고 내역 조회
    List<ReportMemberDetailDTO> selectReportMemberDetail(
            @Param("reportId") Long reportId
    );

    List<ReportCategoryDTO> selectReportCategory(
            @Param("targetId")  Long targetId
    );
}
