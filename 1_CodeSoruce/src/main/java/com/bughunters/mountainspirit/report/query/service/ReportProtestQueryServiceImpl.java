package com.bughunters.mountainspirit.report.query.service;

import com.bughunters.mountainspirit.report.query.dto.ReportProtestQueryDTO;
import com.bughunters.mountainspirit.report.query.mapper.ReportProtestQueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportProtestQueryServiceImpl implements ReportProtestQueryService {

    private ReportProtestQueryMapper reportProtestQueryMapper;

    @Autowired
    public ReportProtestQueryServiceImpl(ReportProtestQueryMapper reportProtestQueryMapper) {
        this.reportProtestQueryMapper = reportProtestQueryMapper;
    }

    @Override
    public List<ReportProtestQueryDTO> selectAllReportProtest() {
        return reportProtestQueryMapper.selectAllReportProtest();
    }

    @Override
    public List<ReportProtestQueryDTO> selectReportProtestByReportedId(Long reportedId) {
        return reportProtestQueryMapper.selectReportProtestByReportedId(reportedId);
    }
}
