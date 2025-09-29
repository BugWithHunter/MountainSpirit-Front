package com.bughunters.mountainspirit.report.command.repository;

import com.bughunters.mountainspirit.report.command.entity.BlacklistCommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistCommandRepository extends JpaRepository<BlacklistCommandEntity, Long> {
}
