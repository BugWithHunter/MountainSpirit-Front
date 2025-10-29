package com.bughunters.mountainspirit.report.command.repository;

import com.bughunters.mountainspirit.report.command.entity.ReportCategoryCommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportCategoryCommandRepository extends JpaRepository<ReportCategoryCommandEntity, Long> {
}
