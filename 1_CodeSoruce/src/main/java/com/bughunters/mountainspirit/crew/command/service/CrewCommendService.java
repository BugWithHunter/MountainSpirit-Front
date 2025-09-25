package com.bughunters.mountainspirit.crew.command.service;

import com.bughunters.mountainspirit.crew.command.entity.Crew;
import com.bughunters.mountainspirit.crew.command.repository.CrewCommendRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CrewCommendService {
    private final CrewCommendRepository crewCommendRepository;

    @Autowired
    public CrewCommendService(CrewCommendRepository crewCommendRepository){
        this.crewCommendRepository = crewCommendRepository;
    }


    public String insertCrew(Crew crew) {
        return null;
    }
}
