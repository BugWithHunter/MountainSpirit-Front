package com.bughunters.mountainspirit.memberrank.command.repository;

import com.bughunters.mountainspirit.memberrank.command.entity.ScoreStandard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreStandardRepository extends JpaRepository<ScoreStandard, Long> {
}
