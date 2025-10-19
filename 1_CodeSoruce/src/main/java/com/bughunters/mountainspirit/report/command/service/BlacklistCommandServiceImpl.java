package com.bughunters.mountainspirit.report.command.service;

import com.bughunters.mountainspirit.report.command.dto.BlacklistRequestDTO;
import com.bughunters.mountainspirit.report.command.entity.BlacklistCommandEntity;
import com.bughunters.mountainspirit.report.command.repository.BlacklistCommandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlacklistCommandServiceImpl implements BlacklistCommandService{
    private final BlacklistCommandRepository blacklistCommandRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BlacklistCommandServiceImpl(BlacklistCommandRepository blacklistCommandRepository,
                                       ModelMapper modelMapper) {
        this.blacklistCommandRepository = blacklistCommandRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addComment(BlacklistRequestDTO dto, Long adminId) {

        BlacklistCommandEntity entity = blacklistCommandRepository.findById(dto.getBlacklistId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 블랙리스트 ID입니다."));


        entity.setAnnotation(dto.getAnnotation());
        entity.setAdminId(adminId);

        blacklistCommandRepository.save(entity);

    }
}
