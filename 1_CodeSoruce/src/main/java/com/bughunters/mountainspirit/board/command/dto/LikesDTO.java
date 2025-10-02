package com.bughunters.mountainspirit.board.command.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LikesDTO {
    private long id;
    private long cumId;
    private Long postId;
}
