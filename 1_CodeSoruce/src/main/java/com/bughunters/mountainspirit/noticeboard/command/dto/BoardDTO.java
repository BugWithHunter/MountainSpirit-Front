package com.bughunters.mountainspirit.noticeboard.command.dto;

import com.bughunters.mountainspirit.noticeboard.command.entity.NoticeBoardImage;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardDTO {
    private long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String isDeleted;
    private long cumId;

    private List<NoticeBoardImage> boardImages;
}
