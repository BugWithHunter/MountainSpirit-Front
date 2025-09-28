package com.bughunters.mountainspirit.mountainreview.command.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="CourseReview")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CourseReview {
    @Id
    @Column(name = "id")
    private long id;

    @CreatedDate
    @Column(name = "commitDate")
    private LocalDateTime commitDate;

    @LastModifiedDate
    @Column(name = "updateDate")
    private LocalDateTime updateDate;

    @Column(name = "reviewContent")
    private String reviewContent;

    @Column(name = "poiId")
    private String poiId;

    @Column(name = "cumId")
    private long cumId;
}
