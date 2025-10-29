package com.bughunters.mountainspirit.noticeboard.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "NoticeBoardImage")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NoticeBoardImage {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "originName")
    private String originName;

    @Column(name = "renaming")
    private String renaming;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "path")
    private String path;

    @Column(name = "postId")
    private long postId;
}
