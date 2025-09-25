package com.bughunters.mountainspirit.member.command.repository;

import com.bughunters.mountainspirit.member.command.entity.ClimbCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClimbCheckRepository extends JpaRepository<ClimbCheck, Long> {
}
