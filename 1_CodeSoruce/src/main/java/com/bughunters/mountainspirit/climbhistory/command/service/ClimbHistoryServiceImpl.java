package com.bughunters.mountainspirit.climbhistory.command.service;

import com.bughunters.mountainspirit.climbhistory.command.dto.FindClimbCheckDTO;
import com.bughunters.mountainspirit.climbhistory.command.infrastructure.FeignClientClimbHisotryToMember;
import com.bughunters.mountainspirit.climbhistory.command.dto.RequestModifyStatusOfMemberDTO;
import com.bughunters.mountainspirit.climbhistory.command.dto.ResponseStatusDTO;
import com.bughunters.mountainspirit.memberrank.command.dto.RequestRankDTO;
import com.bughunters.mountainspirit.climbhistory.command.dto.RequestSubmmitClimbMountainDTO;
import com.bughunters.mountainspirit.climbhistory.command.entity.ClimbCheck;
import com.bughunters.mountainspirit.climbhistory.command.entity.ClimbRecord;
import com.bughunters.mountainspirit.climbhistory.command.repository.ClimbCheckRepository;
import com.bughunters.mountainspirit.climbhistory.command.repository.ClimbRecordRepository;
import com.bughunters.mountainspirit.climbhistory.query.dto.FindClimbCheckQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.dto.RequestStartClimbMountainQueryDTO;
import com.bughunters.mountainspirit.climbhistory.query.service.ClimbHistoryQueryServiceImpl;
import com.bughunters.mountainspirit.memberrank.command.service.MemberRankService;
import com.bughunters.mountainspirit.memberrank.command.dto.ResponseRankDTO;
import com.bughunters.mountainspirit.stamp.command.dto.StampWithCourseAndMountainDTO;
import com.bughunters.mountainspirit.stamp.command.service.StampService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ClimbHistoryServiceImpl implements ClimbHistoryService {

    private final ClimbHistoryQueryServiceImpl climbHistoryQueryServiceImpl;
    private final ModelMapper modelMapper;
    private final ClimbCheckRepository climbCheckRepository;
    private final ClimbRecordRepository climbRecordRepository;
    private final StampService stampService;
    private final FeignClientClimbHisotryToMember memberService;
    private final MemberRankService memberRankService;

    public ClimbHistoryServiceImpl(ClimbHistoryQueryServiceImpl climbHistoryQueryServiceImpl
            , ModelMapper modelMapper
            , ClimbCheckRepository climbCheckRepository
            , ClimbRecordRepository climbRecordRepository
            , StampService stampService
            , FeignClientClimbHisotryToMember memberService
            , MemberRankService memberRankService) {
        this.climbHistoryQueryServiceImpl = climbHistoryQueryServiceImpl;
        this.modelMapper = modelMapper;
        this.climbCheckRepository = climbCheckRepository;
        this.climbRecordRepository = climbRecordRepository;
        this.stampService = stampService;
        this.memberService = memberService;
        this.memberRankService = memberRankService;
    }

    @Override
    @Transactional
    public FindClimbCheckDTO startClimbMountain(RequestSubmmitClimbMountainDTO request) {

        FindClimbCheckQueryDTO findClimbCheckQueryDTO = getFindClimbCheckQueryDTO(request);

        FindClimbCheckDTO findClimbCheckDTO = null;

        //메모. null 이면 등산 시작 데이터 insert
        if (findClimbCheckQueryDTO == null) {
            ClimbCheck climbCheck = modelMapper.map(request, ClimbCheck.class);
            climbCheckRepository.save(climbCheck);
        } else {
            findClimbCheckDTO = modelMapper.map(findClimbCheckQueryDTO, FindClimbCheckDTO.class);
        }

        return findClimbCheckDTO;
    }


    @Override
    @Transactional
    public FindClimbCheckDTO completeClimbMountain(RequestSubmmitClimbMountainDTO request) {

        FindClimbCheckQueryDTO findClimbCheckQueryDTO = getFindClimbCheckQueryDTO(request);

        FindClimbCheckDTO findClimbCheckDTO = null;

        //메모. null이 아니면 등산 완료 처리 및 등산기록 insert, 회원 테이블에 등산 횟수 업데이트
        if (findClimbCheckQueryDTO != null) {
            LocalDateTime completeTime = LocalDateTime.now();
            //메모. 1. 등산 완료 인증 업데이트
            updateClimbCheck(request, findClimbCheckQueryDTO, completeTime);

            //메모. 2.등산 기록 테이블 insert
            insertClimbRecod(request, findClimbCheckQueryDTO, completeTime);

            //메모. 3.코스도장, 산도장 희득 조건 확인 후 도장 흭득(흭득 유무 boolean 반환 점수 반영을 위함)
            com.bughunters.mountainspirit.stamp.command.dto.RequestSubmmitClimbMountainDTO requestDTO
                    = modelMapper.map(request, com.bughunters.mountainspirit.stamp.command.dto.RequestSubmmitClimbMountainDTO.class);
            StampWithCourseAndMountainDTO stampDTO = stampService.copleteClimbingMountain(requestDTO);

            //메모. 4.산마스터 등급 기준 체크, 기준 등급 반환, 산신령 등급으로 올라 갈 경우 기존 산신령 등급 하락
            //메모. 5. 점수 집계
            ResponseRankDTO responseRankDTO = modifyMemberRank(stampDTO);

            RequestModifyStatusOfMemberDTO modifyStatusOfMemberDTO =
                    new RequestModifyStatusOfMemberDTO();
            modifyStatusOfMemberDTO.setSummaryScore(responseRankDTO.getSummaryScore());

            //스코어 관련 객체를 맵으로 생성
            Map<Integer, Long> baseScoreMap =
                    responseRankDTO.getBaseMemberRanks()
                            .stream()
                            .collect(Collectors.toMap(
                                    k -> k.getStandard(),
                                    v -> v.getId(),
                                    (a, b) -> a,
                                    HashMap::new
                            ));
            modifyStatusOfMemberDTO.setBaseMemberRanks(baseScoreMap);
            modifyStatusOfMemberDTO.setCumId(request.getCumId());

            //메모. 6.등급 기준 체크, 등급 업 할 등급 코드 반환
            ResponseStatusDTO responseStatusDTO = memberService.modifyStatusAfterClimbMountian(modifyStatusOfMemberDTO);


            //메모. 7.반환 할 데이터
            //메모. 7-1. 등산 히스토리 , 산, 코스 정보
            findClimbCheckDTO = registResponseDTO(request, findClimbCheckQueryDTO, completeTime, stampDTO, responseRankDTO, responseStatusDTO);
        } else {
        }

        return findClimbCheckDTO;
    }

    private FindClimbCheckDTO registResponseDTO(RequestSubmmitClimbMountainDTO request, FindClimbCheckQueryDTO findClimbCheckQueryDTO, LocalDateTime completeTime, StampWithCourseAndMountainDTO stampDTO, ResponseRankDTO responseRankDTO, ResponseStatusDTO responseStatusDTO) {
        FindClimbCheckDTO findClimbCheckDTO;
        findClimbCheckDTO = modelMapper.map(findClimbCheckQueryDTO, FindClimbCheckDTO.class);
        findClimbCheckDTO.setStateCode("Y");
        findClimbCheckDTO.setEndTime(completeTime);
        findClimbCheckDTO.setCumId(request.getCumId());
        findClimbCheckDTO.setFrtrlId(request.getFrtrlId());
        findClimbCheckDTO.setPoiId(request.getPoiId());

        //메모. 7-2. 스탬프 정보
        findClimbCheckDTO.setGetMountainStamp(stampDTO.isNewMountainStamp());
        findClimbCheckDTO.setGetCourseStamp(stampDTO.isNewCourseStamp());
        findClimbCheckDTO.setMountainStampImagePath(stampDTO.getMountainStampImagePath());
        findClimbCheckDTO.setCourseStampImagePath(stampDTO.getCourseStampImagePath());

        //메모. 7-3. 산별 등급 정보
        findClimbCheckDTO.setModifyMyMountainRank(responseRankDTO.isModifyMyMountainRank());
        findClimbCheckDTO.setModifyRanks(responseRankDTO.getModifyRanks());

        //메모. 7-4. 회원 흭득 점수 및 회원 변경 정보
        findClimbCheckDTO.setSummaryScore(responseRankDTO.getSummaryScore());
        findClimbCheckDTO.setMemRankId(responseStatusDTO.getMemRankId());
        findClimbCheckDTO.setCumNm(responseStatusDTO.getCumNm());

        String memRankNm = "";
        if (responseRankDTO.getBaseMemberRanks().stream()
                .filter(x -> x.getId().equals(responseStatusDTO.getMemRankId()))
                .findFirst()
                .orElse(null) != null) {

            memRankNm = responseRankDTO.getBaseMemberRanks().stream()
                    .filter(x -> x.getId().equals(responseStatusDTO.getMemRankId()))
                    .findFirst()
                    .orElse(null).getName();
        }

        findClimbCheckDTO.setMemRankNm(memRankNm);
        return findClimbCheckDTO;
    }

    private ResponseRankDTO modifyMemberRank(StampWithCourseAndMountainDTO stampDTO) {
        RequestRankDTO requestRankDTO = modelMapper.map(stampDTO, RequestRankDTO.class);

        //메모. 등급 변경 조건 확인 하고 등급 변경
        ResponseRankDTO responseRankDTO = memberRankService.modifyMemberRank(requestRankDTO);

        return responseRankDTO;

    }

    // 등산 기록 삽입
    private void insertClimbRecod(RequestSubmmitClimbMountainDTO request, FindClimbCheckQueryDTO findClimbCheckQueryDTO, LocalDateTime completeTime) {
        ClimbRecord climbRecord = modelMapper.map(request, ClimbRecord.class);
        climbRecord.setStartTime(findClimbCheckQueryDTO.getUpdateTime());
        climbRecord.setEndTime(completeTime);
        climbRecordRepository.save(climbRecord);
    }

    private void updateClimbCheck(RequestSubmmitClimbMountainDTO request, FindClimbCheckQueryDTO findClimbCheckQueryDTO, LocalDateTime completeTime) {
        ClimbCheck climbCheck = modelMapper.map(request, ClimbCheck.class);
        climbCheck.setId(findClimbCheckQueryDTO.getId());
        climbCheck.setStateCode("Y");
        climbCheck.setEndTime(completeTime);
        climbCheckRepository.save(climbCheck);
    }


    private FindClimbCheckQueryDTO getFindClimbCheckQueryDTO(RequestSubmmitClimbMountainDTO request) {
        RequestStartClimbMountainQueryDTO requestStartClimbMountainQueryDTO =
                modelMapper.map(request, RequestStartClimbMountainQueryDTO.class);

        FindClimbCheckQueryDTO findClimbCheckQueryDTO
                = climbHistoryQueryServiceImpl.findClimbCheckByMember(requestStartClimbMountainQueryDTO);
        return findClimbCheckQueryDTO;
    }
}
