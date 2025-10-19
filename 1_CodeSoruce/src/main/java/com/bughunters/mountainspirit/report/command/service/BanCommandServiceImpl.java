package com.bughunters.mountainspirit.report.command.service;

import com.bughunters.mountainspirit.report.command.dto.BanAnnotationRequestDTO;
import com.bughunters.mountainspirit.report.command.entity.BanCommandEntity;
import com.bughunters.mountainspirit.report.command.repository.BanCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BanCommandServiceImpl implements BanCommandService {
    private final BanCommandRepository  banCommandRepository;

    @Autowired
    public BanCommandServiceImpl(BanCommandRepository banCommandRepository) {
        this.banCommandRepository = banCommandRepository;
    }


    @Override
    public void addAnnotation(BanAnnotationRequestDTO dto, Long adminId) {
        BanCommandEntity ban = banCommandRepository.findById(dto.getBanId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 정지 ID입니다."));

        ban.setAnnotation(dto.getAnnotation()); // annotation 값 업데이트
        ban.setAdminId(adminId);               // 수정한 관리자 ID 기록
        banCommandRepository.save(ban);
    }
}
