package com.bughunters.mountainspirit.crewmember.command.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="CrewMember")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrewMember {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="crewMemberJoinDate")
    private String crewMemberJoinDate;
    @Column(name="crewMemberRoleUpdateDate")
    private String crewMemberRoleUpdateDate;
    @Column(name="crewId")
    private Long crewId;
    @Column(name="cumId")
    private Long cumId;
    @Column(name="crewRoleId")
    private Long crewRoleId;
}
