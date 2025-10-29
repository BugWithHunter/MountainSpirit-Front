package com.bughunters.mountainspirit.noticeboard.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardNameDTO {
    private Long id;
    private String title;
    private LocalDateTime CreateDate;
    private Long cumId;
}
