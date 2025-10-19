package com.bughunters.mountainspirit.report.query.service;

import com.bughunters.mountainspirit.report.query.dto.ReportQueryDTO;
import com.bughunters.mountainspirit.report.query.mapper.ReportQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportQueryServiceImpl implements ReportQueryService {

    private ReportQueryMapper reportQueryMapper;

    @Autowired
    public ReportQueryServiceImpl(ReportQueryMapper reportQueryMapper) {
       this.reportQueryMapper = reportQueryMapper;
    }

    @Override
    public List<ReportQueryDTO> selectAllReport() {
        return reportQueryMapper.reportAll();
    }

    @Override
    public List<ReportQueryDTO> searchReportsByType(String reportType) {
        return reportQueryMapper.findReportsByType(reportType);
    }

    @Override
    public List<ReportQueryDTO> selectReportsByTypeAndStatus(String reportType, String isAccepted, int page, int size) {
        int offset = (page - 1) * size;
        return reportQueryMapper.findReportsByTypeAndStatus(reportType, isAccepted, size, offset);
    }

    @Override
    public List<ReportQueryDTO> selectReportsByMemberId(Long memberId) {
        return reportQueryMapper.findReportsByMemberId(memberId);
    }
}
