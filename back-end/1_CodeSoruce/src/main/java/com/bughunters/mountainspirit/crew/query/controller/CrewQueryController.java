package com.bughunters.mountainspirit.crew.query.controller;

import com.bughunters.mountainspirit.common.UserInfo;
import com.bughunters.mountainspirit.crew.query.dto.CrewDTO;
import com.bughunters.mountainspirit.crew.query.dto.CrewInfoDTO;
import com.bughunters.mountainspirit.crew.query.service.CrewQueryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/crew")
public class CrewQueryController {
    private final CrewQueryService crewQueryService;

    @Autowired
    public CrewQueryController(CrewQueryService crewQueryService) {
        this.crewQueryService = crewQueryService;
    }

    @GetMapping("/crew-info")
    public ResponseEntity<List<CrewDTO>> findAllCrews(HttpServletRequest request) {
        log.info("claim 데이터 : {}", request.getAttribute("userInfo"));
        List<CrewDTO> crews = crewQueryService.findAllCrews();
        log.debug(crews.toString());
        return ResponseEntity.ok().body(crews);
    }

    @GetMapping("/crew-info/{crewId}")
    public ResponseEntity<CrewInfoDTO> findOneCrewById(@PathVariable("crewId") Long crewId){
        CrewInfoDTO crew = crewQueryService.findOneCrewById(crewId);
        return ResponseEntity.ok().body(crew);
    }


}
