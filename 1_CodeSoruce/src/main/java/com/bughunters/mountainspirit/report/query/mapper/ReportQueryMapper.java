package com.bughunters.mountainspirit.report.query.mapper;

import com.bughunters.mountainspirit.report.query.dto.ReportQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportQueryMapper {
    List<ReportQueryDTO> reportAll();

    // 동적 조건 조회
    List<ReportQueryDTO> findReportsByTypeAndStatus(
            @Param("reportType") String reportType,
            @Param("isAccepted") String isAccepted
    );
}
