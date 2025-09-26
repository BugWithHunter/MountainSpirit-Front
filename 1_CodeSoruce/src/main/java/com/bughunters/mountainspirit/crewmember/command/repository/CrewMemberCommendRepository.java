package com.bughunters.mountainspirit.crewmember.command.repository;

import com.bughunters.mountainspirit.crewmember.command.entity.CrewApply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewMemberCommendRepository extends JpaRepository<CrewApply, Long> {
}
