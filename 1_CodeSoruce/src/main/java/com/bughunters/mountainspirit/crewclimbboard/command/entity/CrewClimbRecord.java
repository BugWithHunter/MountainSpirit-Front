package com.bughunters.mountainspirit.crewclimbboard.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="CrewClimbRecord")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewClimbRecord {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Character crewClimbHistoryIsSucceed = null;
    @Column
    private Long crewClimbId;
    @Column
    private Long crewMemberId;
    @Column
    private String frtrlId;
}
