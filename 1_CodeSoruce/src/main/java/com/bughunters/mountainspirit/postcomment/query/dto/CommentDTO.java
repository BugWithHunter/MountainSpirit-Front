package com.bughunters.mountainspirit.postcomment.query.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentDTO {
    private long id;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private long cumId;
}
