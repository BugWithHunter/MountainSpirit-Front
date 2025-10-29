package com.bughunters.mountainspirit.report.command.service;

import com.bughunters.mountainspirit.report.command.dto.ReportIsAccepted;
import com.bughunters.mountainspirit.report.command.dto.ReportMemberDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportRequestCommandDTO;
import com.bughunters.mountainspirit.report.command.dto.ReportResponseCommandDTO;
import com.bughunters.mountainspirit.report.command.entity.ReportCategoryCommandEntity;
import com.bughunters.mountainspirit.report.command.entity.ReportCommandEntity;
import com.bughunters.mountainspirit.report.command.infrastructure.MemberFeignClient;
import com.bughunters.mountainspirit.report.command.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportCommandServiceImplTest {

    @Mock
    private ReportCommandRepository reportCommandRepository;

    @Mock
    private ReportCategoryCommandRepository reportCategoryCommandRepository;

    @Mock
    private BanCommandRepository banCommandRepository;

    @Mock
    private BlacklistCommandRepository blacklistCommandRepository;

    @Mock
    private MemberFeignClient memberClient; // 회원 서버 FeignClient Mock

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReportCommandServiceImpl reportCommandService;


}
