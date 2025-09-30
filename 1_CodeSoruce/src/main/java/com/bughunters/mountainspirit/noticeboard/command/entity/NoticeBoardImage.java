package com.bughunters.mountainspirit.noticeboard.command.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private int id;

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
