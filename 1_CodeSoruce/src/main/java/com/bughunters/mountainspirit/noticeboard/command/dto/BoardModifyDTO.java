package com.bughunters.mountainspirit.noticeboard.command.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardModifyDTO {
    private String title;
    private String content;
    private LocalDateTime modifyDate;
}
