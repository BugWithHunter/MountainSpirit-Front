package com.bughunters.mountainspirit.report.command.service;

import com.bughunters.mountainspirit.board.command.entity.Board;
import com.bughunters.mountainspirit.board.command.repository.BoardRepository;
import com.bughunters.mountainspirit.crewboard.command.entity.CrewBoard;
import com.bughunters.mountainspirit.crewboard.command.repository.CrewBoardRepository;
import com.bughunters.mountainspirit.postcomment.command.entity.Comment;
import com.bughunters.mountainspirit.postcomment.command.repository.CommentRepository;
import com.bughunters.mountainspirit.report.command.dto.*;
import com.bughunters.mountainspirit.report.command.entity.*;
import com.bughunters.mountainspirit.report.command.infrastructure.MemberFeignClient;
import com.bughunters.mountainspirit.report.command.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Service
public class ReportCommandServiceImpl implements ReportCommandService {

    private final ReportCommandRepository reportCommandRepository;
    private final ReportCategoryCommandRepository reportCategoryCommandRepository;
    private final BanCommandRepository banCommandRepository;
    private final BlacklistCommandRepository blacklistCommandRepository;
    private final MemberFeignClient memberFeignClient;
    private final ModelMapper modelMapper;
    private final BoardRepository boardRepository;
    private final CrewBoardRepository crewBoardRepository;
    private final CommentRepository commentRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ReportCommandServiceImpl(ReportCommandRepository reportCommandRepository,
                                    ReportCategoryCommandRepository reportCategoryCommandRepository,
                                    BanCommandRepository banCommandRepository,
                                    BlacklistCommandRepository blacklistCommandRepository,
                                    MemberFeignClient memberFeignClient,
                                    ModelMapper modelMapper,
                                    BoardRepository boardRepository,
                                    CrewBoardRepository crewBoardRepository,
                                    CommentRepository commentRepository,
                                    ApplicationEventPublisher eventPublisher) {
        this.reportCommandRepository = reportCommandRepository;
        this.reportCategoryCommandRepository = reportCategoryCommandRepository;
        this.banCommandRepository = banCommandRepository;
        this.blacklistCommandRepository = blacklistCommandRepository;
        this.memberFeignClient = memberFeignClient;
        this.modelMapper = modelMapper;
        this.boardRepository = boardRepository;
        this.commentRepository = commentRepository;
        this.crewBoardRepository = crewBoardRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    @Override
    public ReportResponseCommandDTO createReport(ReportRequestCommandDTO reportRequestCommandDTO) {

        ReportMemberDTO member = memberFeignClient
                .getMemberInfo(reportRequestCommandDTO.getReportedId());

        // suspensionCycle은 member의 bancnt와 동일
        int suspensionCycle = member.getBanCnt();

        ReportCommandEntity rce = modelMapper.map(reportRequestCommandDTO, ReportCommandEntity.class);
        rce.setId(null);


        if(member.getMemStsId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "현재 회원은 회원상태가 없습니다");
        }

        // 회원 상태 체크: 정지(3), 블랙리스트(5)면 신고 불가
        if (member.getMemStsId() == 3L) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "현재 회원은 회원정지 상태입니다");
        }

        if (member.getMemStsId() == 5L) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "현재 회원은 블랙리스트 상태입니다");
        }

        switch(reportRequestCommandDTO.getReportType().toUpperCase()) {
            case "BOARD":
                Board board = boardRepository.findById(Math.toIntExact(reportRequestCommandDTO.getId()))
                        .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다"));
                if (board.getCumId() != reportRequestCommandDTO.getReportedId()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "신고 대상자와 게시글 작성자가 일치하지 않습니다.");
                }
                if (reportCommandRepository.existsByPostIdAndIsAcceptedNot(board, ReportIsAccepted.U)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 신고된 게시글입니다.");
                }
                rce.setPostId(board);
                break;
            case "CREW":
                CrewBoard crew = crewBoardRepository.findById(Math.toIntExact(reportRequestCommandDTO.getId()))
                        .orElseThrow(() -> new IllegalArgumentException("크루 게시글이 존재하지 않습니다"));
                if(crew.getCrewId() != reportRequestCommandDTO.getReportedId()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "신고 대상자와 게시글 작성자가 일치하지 않습니다.");
                }
                if (reportCommandRepository.existsByCrewPostIdAndIsAcceptedNot(crew, ReportIsAccepted.U)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 신고된 크루 게시글입니다.");
                }
                rce.setCrewPostId(crew);
                break;
            case "COMMENT":
                Comment comment = commentRepository.findById(Math.toIntExact(reportRequestCommandDTO.getId()))
                        .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다"));
                if(comment.getCumId() != reportRequestCommandDTO.getReportedId()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "신고 대상자와 게시글 작성자가 일치하지 않습니다.");
                }
                if (reportCommandRepository.existsByReplyIdAndIsAcceptedNot(comment, ReportIsAccepted.U)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 신고된 댓글입니다.");
                }
                rce.setReplyId(comment);
                break;
            case "MEMBER":
                break;
            default:
                throw new IllegalArgumentException("잘못된 신고 타입입니다.");
        }

        // Report 엔티티 매핑
        rce.setReportDate(LocalDateTime.now());
        rce.setSuspensionCycle(suspensionCycle);
        rce.setIsAccepted(ReportIsAccepted.N);

        reportCommandRepository.save(rce);

        ModelMapper tempMapper = new ModelMapper();
        tempMapper.typeMap(ReportCommandEntity.class, ReportResponseCommandDTO.class)
                .addMappings(mapper -> mapper.skip(ReportResponseCommandDTO::setId));

        //응답
        ReportResponseCommandDTO rrcdto = tempMapper.map(rce, ReportResponseCommandDTO.class);
        rrcdto.setId(rce.getId());
        rrcdto.setResult("PENDING");
        return rrcdto;
    }


    @Override
    @Transactional
    public ReportResponseCommandDTO updateReportStatus(Long reportedId, ReportIsAccepted status) {
        // report 조회
        ReportCommandEntity report = reportCommandRepository.findById(reportedId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "신고를 찾을 수 없습니다"));

        // 상태 업데이트
        report.setIsAccepted(status);
        reportCommandRepository.save(report);

        String result = "UPDATED";

        if (status == ReportIsAccepted.Y) {
            // 외부 회원정보는 Feign으로 조회(후에 회원 업데이트는 트랜잭션 후로 분리)
            ReportMemberDTO member = memberFeignClient.getMemberInfo(report.getReportedId());

            if (report.getPostId() != null) {
                 Board board = report.getPostId();
                 board.setIsDeleted("Y");
                 boardRepository.save(board);
            }
            else if (report.getReplyId() != null) {
                 Comment comment = report.getReplyId();
                 try {
                     commentRepository.delete(comment);
                 } catch (Exception e) {
                     log.warn("댓글 삭제 중 예외 발생 (무시됨): {}", e.getMessage());
                 }
            }
            else if (report.getCrewPostId() != null) {
                 CrewBoard crew = report.getCrewPostId();
                 crew.setIsDeleted("Y");
                 crewBoardRepository.save(crew);
            }
            // 카테고리 정보
            ReportCategoryCommandEntity rcce = reportCategoryCommandRepository
                    .findById(report.getCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "카테고리를 찾을 수 없습니다"));

            // member.getBanCnt()와 report.suspensionCycle이 같은 인스턴스 갯수(승인된것만)
            long instanceCntNum = reportCommandRepository.countByReportedIdAndSuspensionCycleAndCategoryIdAndIsAccepted(
                   report.getReportedId(),
                   member.getBanCnt(),
                   report.getCategoryId(),
                   ReportIsAccepted.Y
           );

            // 카테고리 countStandard == 0 이면 즉시 블랙리스트
            if (rcce.getCountStandard() == 0) {
                createBlacklistAndMemberUpdate(member, report);
                result = "BLACKLIST";
            }
            // countStandard 도달 또는 10개 누적이면 BAN(회원정지 테이블 insert + 회원 banCnt 증가)
            else if (instanceCntNum == rcce.getCountStandard() || instanceCntNum == 10) {
                boolean isBlacklist = createBanAndMemberUpdate(member, report);
                result = isBlacklist? "BLACKLIST" : "BAN";
            }
        } else if (status == ReportIsAccepted.U) {
            if (report.getPostId() != null) {
                Board board = report.getPostId();
                board.setIsDeleted("N");
                boardRepository.save(board);
            } else if (report.getCrewPostId() != null) {
                CrewBoard crew = report.getCrewPostId();
                crew.setIsDeleted("N");
                crewBoardRepository.save(crew); }
            result = "UNLOCKED";
        }

        ReportResponseCommandDTO rrcdto = modelMapper.map(report, ReportResponseCommandDTO.class);
        rrcdto.setResult(result);
        return rrcdto;
    }

    // 회원정지(Ban) 생성 및 commit 이후 회원 테이블 업데이트 이벤트 발행
    private boolean createBanAndMemberUpdate(ReportMemberDTO member, ReportCommandEntity report) {
        // 이미 블랙리스트면 무시
        if (member.getMemStsId() == 5L) {
            return false;
        }

        int newBanCnt = member.getBanCnt() + 1;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = null;
        Long nextstatus = 3L;

        // newBanCnt 기준으로 기간 설정
        if (newBanCnt == 1) {
            endDate = startDate.plusDays(7);
        } else if (newBanCnt == 2) {
            endDate = startDate.plusDays(15);
        } else if (newBanCnt == 3) {
            endDate = startDate.plusDays(30);
        } else if (newBanCnt == 4) {
            nextstatus = 5L;
            createBlacklistAndMemberUpdate(member, report);
            return true;
        } else if (newBanCnt >= 5) {
            return false;
        }

        BanCommandEntity bcEntity = BanCommandEntity.builder()
                .startDate(startDate)
                .endDate(endDate)
                .userId(report.getReportedId())
                .build();
        banCommandRepository.save(bcEntity);

        // DB 커밋 후 외부 회원서비스로 상태(정지, banCnt) 업데이트 -> 이벤트 발행 (AFTER_COMMIT 리스너에서 Feign 호출)
        eventPublisher.publishEvent(new MemberStatusUpdateEvent(member.getId(), nextstatus, newBanCnt));
        return false;
    }

    // 블랙리스트 생성 및 commit 후 회원 테이블 업데이트 이벤트 발행
    private void createBlacklistAndMemberUpdate(ReportMemberDTO member, ReportCommandEntity report) {
        if(member.getMemStsId() == 5L) {
            return;
        }

        BlacklistCommandEntity bce = BlacklistCommandEntity.builder()
                .createDate(LocalDateTime.now())
                .memberId(report.getReportedId())
                .build();
        blacklistCommandRepository.save(bce);

        //memStsId = 5L (블랙리스트), banCnt는 현재 값 유지 또는 필요에 따라 보낼 수 있음
        eventPublisher.publishEvent(new MemberStatusUpdateEvent(member.getId(), 5L, member.getBanCnt()));
    }

}
