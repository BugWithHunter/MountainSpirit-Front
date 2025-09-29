package com.bughunters.mountainspirit.crewboard.command.dto;

import com.bughunters.mountainspirit.crewboard.command.entity.CrewBoardImage;
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
    private String isNotice;
    private long crewId;
    private long cumId;

    private List<CrewBoardImage> crewBoardImages;
}
