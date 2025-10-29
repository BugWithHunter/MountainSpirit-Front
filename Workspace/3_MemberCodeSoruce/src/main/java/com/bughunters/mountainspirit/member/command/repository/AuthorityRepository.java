package com.bughunters.mountainspirit.member.command.repository;

import com.bughunters.mountainspirit.member.command.dto.Authority;
import com.bughunters.mountainspirit.member.command.entity.AuthorityList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<AuthorityList, Integer> {
}
