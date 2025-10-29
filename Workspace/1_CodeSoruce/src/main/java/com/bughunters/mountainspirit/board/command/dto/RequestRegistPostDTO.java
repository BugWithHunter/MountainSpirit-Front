package com.bughunters.mountainspirit.board.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestRegistPostDTO {
    private String title;
    private String content;
}
