package com.bughunters.mountainspirit.mountainbookmark.command.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="CourseBookmark")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CourseBookmark {
    @Id
    @Column(name = "id")
    private long id;

    @CreatedDate
    @Column(name = "bookmarkDate")
    private LocalDateTime bookmarkDate;

    @Column(name = "cumId")
    private long cumId;

    @Column(name = "poiId")
    private String poiId;
}
