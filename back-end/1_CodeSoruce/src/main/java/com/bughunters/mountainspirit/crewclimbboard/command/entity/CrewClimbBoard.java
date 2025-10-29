package com.bughunters.mountainspirit.crewclimbboard.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="CrewClimbBoard")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewClimbBoard {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="crewClimbStartDate")
    private String crewClimbStartDate;
    @Column(name="crewClimbRecruitStartDate")
    private String crewClimbRecruitStartDate;
    @Column(name="crewClimbRecruitEndDate")
    private String crewClimbRecruitEndDate;
    @Column(name="crewClimbContent")
    private String crewClimbContent;
    @Column(name="crewClimbIsDeleted")
    private char crewClimbIsDeleted;
    @Column(name="crewClimbAmountOfPeople")
    private Integer crewClimbAmountOfPeople;
    @Column(name="crewClimbIsEnded")
    private char crewClimbIsEnded;
    @Column(name="crewId")
    private Long crewId;
    @Column(name="frtrlId")
    private String frtrlId;
    @Column(name="crewMemberId")
    private Long crewMemberId;
}
