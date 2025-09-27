package com.bughunters.mountainspirit.climbhistory.command.repository;

import com.bughunters.mountainspirit.climbhistory.command.entity.ClimbCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClimbCheckRepository extends JpaRepository<ClimbCheck, Long> {
}
