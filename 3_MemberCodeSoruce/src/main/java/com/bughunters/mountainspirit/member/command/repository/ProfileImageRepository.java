package com.bughunters.mountainspirit.member.command.repository;

import com.bughunters.mountainspirit.member.command.entity.Member;
import com.bughunters.mountainspirit.member.command.entity.ProfileOfMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileOfMember, Long> {
}
