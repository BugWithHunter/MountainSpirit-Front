package com.bughunters.mountainspirit.crewclimbboard.command.repository;

import com.bughunters.mountainspirit.crewclimbboard.command.entity.CrewClimbRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewClimbRecordCommendRepository extends JpaRepository<CrewClimbRecord,Long> {

    int countByCrewClimbId(Long crewClimbId);

    CrewClimbRecord findByCrewClimbIdAndCrewMemberId(Long crewClimbId, Long crewMemberId);

    void deleteByCrewClimbId(Long crewClimbId);

    void deleteAllByCrewClimbId(Long crewClimbId);
}
