package com.bughunters.mountainspirit.report.query.mapper;

import com.bughunters.mountainspirit.report.query.dto.AppealDetailByIdDTO;
import com.bughunters.mountainspirit.report.query.dto.ReportProtestQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportProtestQueryMapper {
    List<ReportProtestQueryDTO> selectAllReportProtest(
            @Param("limit") Integer limit,
            @Param("offset") Integer offset
    );
    List<ReportProtestQueryDTO> selectReportProtestByReportedId(
            @Param("reportedId") Long reportedId,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset
    );
    List<AppealDetailByIdDTO> findAppealDetailById(
            @Param("appealId") Long appealId
    );
}
