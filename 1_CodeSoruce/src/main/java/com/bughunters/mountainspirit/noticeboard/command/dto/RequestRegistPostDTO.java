package com.bughunters.mountainspirit.noticeboard.command.dto;

import com.bughunters.mountainspirit.noticeboard.command.entity.NoticeBoardImage;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestRegistPostDTO {
    private String title;
    private String content;
}
