package com.bughunters.mountainspirit.crewclimbboard.command.repository;

import com.bughunters.mountainspirit.crewclimbboard.command.entity.CrewClimbBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrewClimbBoardCommendRepository extends JpaRepository<CrewClimbBoard,Long> {
}
