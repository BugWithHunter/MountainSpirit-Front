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
    public List<ReportQueryDTO> selectReportsByTypeAndStatus(String reportType, String isAccepted) {
        return reportQueryMapper.findReportsByTypeAndStatus(reportType, isAccepted);
    }
}
