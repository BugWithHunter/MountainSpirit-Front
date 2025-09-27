package com.bughunters.mountainspirit.mountainreview.command.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="MountainReview")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MountainReview {
    @Id
    @Column(name = "id")
    private long id;

    @CreatedDate
    @Column(name = "registTime")
    private LocalDateTime registTime;

    @LastModifiedDate
    @Column(name = "updateTime")
    private LocalDateTime updateTime;

    @Column(name = "content")
    private String content;

    @Column(name = "cumId")
    private long cumId;

    @Column(name = "frtrlId")
    private String frtrlId;
}
