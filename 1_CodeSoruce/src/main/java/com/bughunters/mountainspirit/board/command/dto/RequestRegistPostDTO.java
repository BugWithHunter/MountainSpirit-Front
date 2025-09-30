package com.bughunters.mountainspirit.board.command.dto;

import com.bughunters.mountainspirit.board.command.entity.BoardImage;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
