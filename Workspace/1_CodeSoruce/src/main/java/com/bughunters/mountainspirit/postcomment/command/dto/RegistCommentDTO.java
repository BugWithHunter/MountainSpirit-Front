package com.bughunters.mountainspirit.postcomment.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RegistCommentDTO {
    private String content;
    private Long pid;
    private Long postId;
    private Long crewPostId;
    private Long noticePostId;
}
