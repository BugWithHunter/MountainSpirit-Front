package com.bughunters.mountainspirit.crewmember.command.repository;

import com.bughunters.mountainspirit.crewmember.command.entity.CrewMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewMemberCommendRepository extends JpaRepository<CrewMember, Long> {
    CrewMember findByCrewIdAndCumId(Long crewId, Long cumId);

}
