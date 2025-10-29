package com.bughunters.mountainspirit.member.command.repository;

import com.bughunters.mountainspirit.member.command.entity.ProfileOfMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ProfileImageRepository extends JpaRepository<ProfileOfMember, Long> {
    ProfileOfMember findByCumId(Long id);
}
