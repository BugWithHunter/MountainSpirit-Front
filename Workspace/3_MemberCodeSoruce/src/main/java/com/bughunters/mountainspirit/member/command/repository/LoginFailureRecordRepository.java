package com.bughunters.mountainspirit.member.command.repository;

import com.bughunters.mountainspirit.member.command.entity.LoginFailureRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginFailureRecordRepository extends JpaRepository<LoginFailureRecord,Long> {

}
