package com.bughunters.mountainspirit.crewboard.command.dto;

import com.bughunters.mountainspirit.crewboard.command.entity.CrewBoardImage;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardDTO {
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String isDeleted;
    private String isNotice;
    private long crewId;
    private long cumId;

    private List<MultipartFile> multiFiles;
}
