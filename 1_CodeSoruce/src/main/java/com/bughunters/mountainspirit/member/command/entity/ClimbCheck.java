package com.bughunters.mountainspirit.member.command.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ClimbCheck")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClimbCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" )
    private Long id;

    @Column(name = "stateCode")
    private String stateCode;

    //메모. insertable = false, updatable = false DB defualt 속성 사용하기위해 쿼리에서 제외
    @Column(name = "updateTime" , insertable = false, updatable = false)
    private LocalDateTime updateTime;

    @Column(name = "endTime")
    private LocalDateTime endTime;

    @Column(name = "cumId", nullable = false)
    private Long cumId;

    @Column(name = "frtrlId")
    private String frtrlId;

    @Column(name = "poiId", nullable = false)
    private String poiId;
}
