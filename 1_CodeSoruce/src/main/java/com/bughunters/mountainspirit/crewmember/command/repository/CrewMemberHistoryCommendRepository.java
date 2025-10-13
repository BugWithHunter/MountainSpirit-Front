package com.bughunters.mountainspirit.crewmember.command.repository;

import com.bughunters.mountainspirit.crewmember.command.entity.CrewMemberHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrewMemberHistoryCommendRepository extends JpaRepository<CrewMemberHistory,Long> {
    List<CrewMemberHistory> findAllByCrewIdAndCumIdAndCrewMemberHistoryState(Long crewId, Long cumId, String banned);

    void deleteByCrewIdAndCumId(Long crewId, Long cumId);

    CrewMemberHistory findTopByCrewIdAndCumIdOrderByCrewMemberHistoryJoinDate(Long crewId, Long cumId);
}
