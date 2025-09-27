package com.bughunters.mountainspirit.crewmember.command.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name="Member")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member {
    @Id
    @Column
    private Long id;
    @Column(name="memId")
    private String memId;
    @Column(name="email")
    private String email;
    @Column(name="nickname")
    private String nickname;
    @Column(name="memPwd")
    private String memPwd;
    @Column(name="memName")
    private String memName;
    @Column(name="birth")
    private String birth;
    @Column(name="gender")
    private char gender;
    @Column(name="signInDate")
    private String signInDate;
    @Column(name="lastLogin")
    private String lastLogin;
    @Column(name="climbCnt")
    private Long climbCnt;
    @Column(name="banCnt")
    private Integer banCnt;
    @Column(name="loginFailCnt")
    private Integer loginFailCnt;
    @Column(name="quitDate")
    private String quitDate;
    @Column(name="loginLockUntil")
    private String loginLockUntil;
    @Column(name="score")
    private Integer score;
    @Column(name="memRankId")
    private Long memRankId;
    @Column(name="memStsId")
    private Long memStsId;
    @Column(name="crewId")
    private Long crewId;

}
