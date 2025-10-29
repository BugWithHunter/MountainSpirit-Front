package com.bughunters.mountainspirit.crewboard.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewBoardNameDTO {
    private Long id;
    private String title;
    private LocalDateTime CreateDate;
    private Long cumId;
}
