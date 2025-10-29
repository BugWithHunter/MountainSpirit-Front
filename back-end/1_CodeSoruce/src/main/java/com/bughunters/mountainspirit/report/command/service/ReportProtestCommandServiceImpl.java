package com.bughunters.mountainspirit.report.command.service;

import com.bughunters.mountainspirit.report.command.dto.ReportIsAccepted;
import com.bughunters.mountainspirit.report.command.dto.ReportProtestRequestDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportProtestResponseDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportProtestStatusUpdateDTO;
import com.bughunters.mountainspirit.report.command.entity.ReportProtestCommandEntity;
import com.bughunters.mountainspirit.report.command.repository.ReportCommandRepository;
import com.bughunters.mountainspirit.report.command.repository.ReportProtestCommandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

@Service
public class ReportProtestCommandServiceImpl implements ReportProtestCommandService {

    private final ReportProtestCommandRepository reportProtestCommandRepository;
    private final ReportCommandRepository reportCommandRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReportProtestCommandServiceImpl(ReportProtestCommandRepository reportProtestCommandRepository,
                                           ModelMapper modelMapper,
                                           ReportCommandRepository reportCommandRepository) {
        this.reportProtestCommandRepository = reportProtestCommandRepository;
        this.reportCommandRepository = reportCommandRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ReportProtestResponseDTO createReportProtest(ReportProtestRequestDTO dto) {

        Long reportId = dto.getReportId();
        Long reportedId = dto.getReportedId();

        boolean reportExists = reportCommandRepository.existsByIdAndReportedId(reportId, reportedId);

        if(!reportExists){
            throw new RuntimeException(
                    "해당 reportId에 맞는 reportedId가 존재하지 않아 이의신청을 등록할 수 없습니다. " +
                            "reportId: " + reportId + ", reportedId: " + reportedId);
        }

        ReportProtestCommandEntity reportProtestCommandEntity = ReportProtestCommandEntity.builder()
                .appealDate(LocalDateTime.now())
                .adminId(null)
                .appealReason(dto.getReason())
                .isAccepted("U")
                .reportedId(dto.getReportedId())
                .reportId(dto.getReportId())
                .build();

        ReportProtestCommandEntity savedEntity = reportProtestCommandRepository.save(reportProtestCommandEntity);

        return modelMapper.map(savedEntity, ReportProtestResponseDTO.class);
    }

    @Transactional
    @Override
    public ReportProtestResponseDTO updateProtestStatus(Long protestId, Long adminId, ReportProtestStatusUpdateDTO dto) {

        ReportProtestCommandEntity protest = reportProtestCommandRepository.findById(protestId)
                .orElseThrow(() -> new RuntimeException("이의신청이 존재하지 않습니다. ID: "+ protestId));

        protest.setIsAccepted(dto.getStatus());
        protest.setAdminId(String.valueOf(adminId));

        if(dto.getChangeStatusReason() != null && !dto.getChangeStatusReason().trim().isEmpty()) {
            protest.setChangeStatusReason(dto.getChangeStatusReason());
        }

        if("Y".equals(dto.getStatus())) {
            reportCommandRepository.updateReportStatusByReportId(
                    protest.getReportId(),
                    ReportIsAccepted.N);
        }

        ReportProtestCommandEntity updated = reportProtestCommandRepository.save(protest);

        return modelMapper.map(updated, ReportProtestResponseDTO.class);
    }
}
