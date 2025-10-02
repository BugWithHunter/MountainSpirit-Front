package com.bughunters.mountainspirit.board.command.dto;

import com.bughunters.mountainspirit.board.command.entity.BoardImage;
import com.bughunters.mountainspirit.board.query.dto.ThumbnailDTO;
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
    private long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String isDeleted;
    private long cumId;

    private List<MultipartFile> multiFiles;
}
