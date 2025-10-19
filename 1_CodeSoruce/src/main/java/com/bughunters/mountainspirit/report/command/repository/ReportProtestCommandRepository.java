package com.bughunters.mountainspirit.report.command.repository;

import com.bughunters.mountainspirit.report.command.entity.ReportProtestCommandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportProtestCommandRepository extends JpaRepository<ReportProtestCommandEntity, Long> {
}
