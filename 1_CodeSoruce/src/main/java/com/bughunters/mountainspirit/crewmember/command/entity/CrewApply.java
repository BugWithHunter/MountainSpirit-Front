package com.bughunters.mountainspirit.crewmember.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="CrewApply")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewApply {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="crewApplyDate")
    private String crewApplyDate;
    @Column(name="crewId")
    private Long crewId;
    @Column(name="cumId")
    private Long cumId;
}
