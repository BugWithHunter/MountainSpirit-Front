package com.bughunters.mountainspirit.crewmember.command.repository;

import com.bughunters.mountainspirit.crewmember.command.entity.CrewMemberHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewMemberHistoryCommendRepository extends JpaRepository<CrewMemberHistory,Long> {
}
