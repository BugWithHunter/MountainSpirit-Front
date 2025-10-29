package com.bughunters.mountainspirit.crewmember.command.repository;

import com.bughunters.mountainspirit.crewmember.command.entity.CrewApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CrewApplyCommendRepository extends JpaRepository<CrewApply, Long> {
    CrewApply findByCrewIdAndCumId(Long crewId, Long cumId);

    boolean existsByCrewIdAndCumId(Long crewId, Long cumId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM CrewApply m WHERE m.cumId = :cumId")
    int deleteByCumId(@Param("cumId") Long cumId);
}
