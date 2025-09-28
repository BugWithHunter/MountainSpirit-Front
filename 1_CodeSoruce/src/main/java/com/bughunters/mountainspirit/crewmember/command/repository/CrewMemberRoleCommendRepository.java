package com.bughunters.mountainspirit.crewmember.command.repository;

import com.bughunters.mountainspirit.crewmember.command.entity.CrewMemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewMemberRoleCommendRepository extends JpaRepository<CrewMemberRole,Long> {
    CrewMemberRole findByCrewRoleName(String crewRoleName);
}
