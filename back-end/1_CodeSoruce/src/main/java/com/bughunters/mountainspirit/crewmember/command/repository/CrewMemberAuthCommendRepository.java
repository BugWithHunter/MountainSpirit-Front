package com.bughunters.mountainspirit.crewmember.command.repository;

import com.bughunters.mountainspirit.crewmember.command.entity.CrewMemberAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrewMemberAuthCommendRepository extends JpaRepository<CrewMemberAuth,Long> {

    CrewMemberAuth findByCrewRoleIdAndCrewRoleAuth(Long id, String crewRoleOriginalAuth);
}
