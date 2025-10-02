package com.bughunters.mountainspirit.report.command.repository;

import com.bughunters.mountainspirit.report.command.entity.BanCommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BanCommandRepository extends JpaRepository<BanCommandEntity, Long> {
    int countByUserId(Long reportedId);

    BanCommandEntity findTopByUserIdOrderByEndDateDesc(Long id);
}
