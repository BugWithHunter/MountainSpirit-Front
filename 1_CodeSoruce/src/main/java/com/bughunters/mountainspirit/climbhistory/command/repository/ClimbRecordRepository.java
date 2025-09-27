package com.bughunters.mountainspirit.climbhistory.command.repository;

import com.bughunters.mountainspirit.climbhistory.command.entity.ClimbRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClimbRecordRepository extends JpaRepository<ClimbRecord, Long> {
}
