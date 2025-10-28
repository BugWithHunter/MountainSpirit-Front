package com.bughunters.mountainspirit.report.command.service;

import com.bughunters.mountainspirit.report.command.dto.MemberStatusUpdateEvent;
import com.bughunters.mountainspirit.report.command.dto.ReportMemberUpdateDTO;
import com.bughunters.mountainspirit.report.command.infrastructure.MemberFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class MemberStatusUpdateListener {

    private final MemberFeignClient memberFeignClient;

    @Autowired
    public MemberStatusUpdateListener(MemberFeignClient memberFeignClient) {
        this.memberFeignClient = memberFeignClient;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleMemberStatusUpdate(MemberStatusUpdateEvent event){
        // 트랜잭션이 커밋된 후 실행 -> DB 락 회피
        ReportMemberUpdateDTO dto = new ReportMemberUpdateDTO(event.memberId(), event.memStsId(),event.banCnt());
        memberFeignClient.updateMemberStatus(event.memberId(), dto);
    }
}
