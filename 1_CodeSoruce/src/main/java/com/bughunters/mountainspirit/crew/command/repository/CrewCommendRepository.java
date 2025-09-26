package com.bughunters.mountainspirit.crew.command.repository;

import com.bughunters.mountainspirit.crew.command.entity.Crew;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CrewCommendRepository extends JpaRepository<Crew,Long> {

}
