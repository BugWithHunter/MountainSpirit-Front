package com.bughunters.mountainspirit.noticeboard.command.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "NoticeBoard")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NoticeBoard {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "createDate")
    private LocalDateTime createDate;

    @Column(name = "modifyDate")
    private LocalDateTime modifyDate;

    @Column(name = "isDeleted")
    private String isDeleted;

    @Column(name = "cumId")
    private long cumId;
}
