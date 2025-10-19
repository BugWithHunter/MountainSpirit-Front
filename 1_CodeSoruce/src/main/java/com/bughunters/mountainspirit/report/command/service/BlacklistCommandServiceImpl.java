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


    @Autowired
    public BlacklistCommandServiceImpl(BlacklistCommandRepository blacklistCommandRepository) {
        this.blacklistCommandRepository = blacklistCommandRepository;

    }

    @Override
    public void addComment(Long blacklistId, BlacklistRequestDTO dto, Long adminId) {

        BlacklistCommandEntity entity = blacklistCommandRepository.findById(blacklistId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 블랙리스트 ID입니다."));


        entity.setAnnotation(dto.getAnnotation());
        entity.setAdminId(adminId);

        blacklistCommandRepository.save(entity);

    }
}
