package com.bughunters.mountainspirit.mountainbookmark.command.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="MountainBookmark")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MountainBookmark {
    @Id
    @Column(name = "id")
    private long id;

    @CreatedDate
    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "frtrlId")
    private String frtrlId;

    @Column(name = "cumId")
    private long cumId;
}
