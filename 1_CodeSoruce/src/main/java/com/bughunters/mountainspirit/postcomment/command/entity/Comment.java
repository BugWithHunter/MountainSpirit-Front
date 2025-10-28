package com.bughunters.mountainspirit.postcomment.command.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "createDate")
    private LocalDateTime createDate;

    @Column(name = "modifyDate")
    private LocalDateTime modifyDate;

    @Column(name = "cumId")
    private long cumId;

    @Column(name = "pid")
    private Long pid;

    @Column(name = "postId")
    private Long postId;

    @Column(name = "noticePostId")
    private Long noticePostId;

    @Column(name = "crewPostId")
    private Long crewPostId;
}
