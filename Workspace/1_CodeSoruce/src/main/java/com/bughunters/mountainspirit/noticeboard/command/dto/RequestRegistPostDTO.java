package com.bughunters.mountainspirit.noticeboard.command.dto;

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
