package com.bughunters.mountainspirit.crewmember.command.repository;

import com.bughunters.mountainspirit.crewmember.command.entity.CrewApplyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewApplyHistoryCommendRepository extends JpaRepository<CrewApplyHistory,Long> {
}
