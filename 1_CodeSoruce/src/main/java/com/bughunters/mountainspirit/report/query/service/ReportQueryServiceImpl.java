package com.bughunters.mountainspirit.report.query.service;

import com.bughunters.mountainspirit.report.query.dto.AppealDetailByIdDTO;
import com.bughunters.mountainspirit.report.query.dto.ReportCheckDTO;
import com.bughunters.mountainspirit.report.query.dto.ReportDetailDTO;
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
    public List<ReportQueryDTO> selectAllReport(int page, int size) {
        int offset = (page - 1) * size;
        return reportQueryMapper.reportAll(size, offset);
    }

    @Override
    public List<ReportCheckDTO> searchReportsByType(String reportType, int page, int size) {
        int offset = (page - 1) * size;
        return reportQueryMapper.findReportsByType(reportType, size, offset);
    }

    @Override
    public List<ReportCheckDTO> selectReportsByTypeAndStatus(String reportType, String isAccepted, int page, int size) {
        int offset = (page - 1) * size;
        return reportQueryMapper.findReportsByTypeAndStatus(reportType, isAccepted, size, offset);
    }

    @Override
    public List<ReportDetailDTO> selectReportDetailById(Long id) {
        return reportQueryMapper.findReportDetailById(id);
    }

    @Override
    public List<ReportQueryDTO> selectReportsByMemberId(Long memberId, int page, int size) {
        int offset = (page - 1) * size;
        return reportQueryMapper.findReportsByMemberId(memberId, size, offset);
    }

}
