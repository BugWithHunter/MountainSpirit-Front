package com.bughunters.mountainspirit.report.query.mapper;

import com.bughunters.mountainspirit.report.query.dto.ReportProtestQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportProtestQueryMapper {
    List<ReportProtestQueryDTO> selectAllReportProtest();
    List<ReportProtestQueryDTO> selectReportProtestByReportedId(Long reportedId);
}
