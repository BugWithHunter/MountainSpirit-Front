package com.bughunters.mountainspirit.memberrank.command.service;

import com.bughunters.mountainspirit.memberrank.command.dto.RequestRankDTO;
import com.bughunters.mountainspirit.memberrank.command.dto.ResponseMountainRankDTO;
import com.bughunters.mountainspirit.memberrank.command.dto.ResponseRankDTO;
import com.bughunters.mountainspirit.memberrank.command.entity.MemberRank;
import com.bughunters.mountainspirit.memberrank.command.entity.MountainRank;
import com.bughunters.mountainspirit.memberrank.command.entity.MountainRankStandard;
import com.bughunters.mountainspirit.memberrank.command.entity.ScoreStandard;
import com.bughunters.mountainspirit.memberrank.command.repository.*;
import com.bughunters.mountainspirit.memberrank.query.dto.HigherClimber;
import com.bughunters.mountainspirit.memberrank.query.service.MemberRankQueryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberRankServiceImpl implements MemberRankService {

    private final MountainRankRepository mountainsRankRepository;
    private final MemberRankRepository memberRankRepository;
    private final MountainRankStandardRepository mountainRankStandardRepository;
    private final MemberRankQueryService memberRankQueryService;
    private final MemberRankRepositroy memberRankRepositroy;
    private final ScoreStandardRepository scoreStandardRepository;
    private final ModelMapper modelMapper;

    public MemberRankServiceImpl(MountainRankRepository mountainsRankRepository
            , MemberRankRepository memberRankRepository
            , MountainRankStandardRepository mountainRankStandardRepository
            , MemberRankQueryService memberRankQueryService
            , ModelMapper modelMapper
            , MemberRankRepositroy memberRankRepositroy
            , ScoreStandardRepository scoreStandardRepository) {
        this.mountainsRankRepository = mountainsRankRepository;
        this.memberRankRepository = memberRankRepository;
        this.mountainRankStandardRepository = mountainRankStandardRepository;
        this.memberRankQueryService = memberRankQueryService;
        this.modelMapper = modelMapper;
        this.memberRankRepositroy = memberRankRepositroy;
        this.scoreStandardRepository = scoreStandardRepository;
    }

    @Override
    public ResponseRankDTO modifyMemberRank(RequestRankDTO requestRankDTO) {

        ResponseRankDTO responseRankDTO = new ResponseRankDTO();
        //응답으로 전송 할 데이터(내 등급, 이전 산신령이 있었을 경우 이전 산신령 정보)
        List<ResponseMountainRankDTO> responseMountainRankDTOs = new ArrayList<>();

        //회원별 산에대한 등급 변경
        modifyMountainRankOfMember(requestRankDTO, responseMountainRankDTOs);

        //점수 계산
        int score = calculateScore(requestRankDTO);

        //산 등급변경 정보를 반환 객체에 담아 요청으로 전달
        responseRankDTO.setModifyRanks(responseMountainRankDTOs);

        if (responseMountainRankDTOs.size() > 0) {
            responseRankDTO.setModifyMyMountainRank(true);
        }

        // 점수별 등급 기준
        List<MemberRank> memberRanks = memberRankRepositroy.findAll();
        responseRankDTO.setBaseMemberRanks(memberRanks);
        responseRankDTO.setSummaryScore(score);

        return responseRankDTO;
    }

    private int calculateScore(RequestRankDTO requestRankDTO) {
        int score = 0;

        // 베이스 점수표
        List<ScoreStandard> baseScores = scoreStandardRepository.findAll();

        //1. 코스 난이도 별 점수
        ScoreStandard baseScoreForCourse = baseScores.stream()
                .filter(x -> x.getId().equals(requestRankDTO.getCourseRank()))
                .findFirst()
                .orElse(null);
        score += baseScoreForCourse != null ? baseScoreForCourse.getScore() : 0;

        //2. 코스 도장을 흭득한 경우
        if (requestRankDTO.isNewCourseStamp()) {
            ScoreStandard baseScoreForStamp = baseScores.stream()
                    .filter(x -> x.getId().equals(4L))
                    .findFirst()
                    .orElse(null);
            score += baseScoreForStamp != null ? baseScoreForStamp.getScore() : 0;
        }

        //3. 산 도장을 흭득한 경우
        if (requestRankDTO.isNewCourseStamp()) {
            ScoreStandard baseScoreForStamp = baseScores.stream()
                    .filter(x -> x.getId().equals(5L))
                    .findFirst()
                    .orElse(null);
            score += baseScoreForStamp != null ? baseScoreForStamp.getScore() : 0;
        }

        return score;
    }

    private void modifyMountainRankOfMember(RequestRankDTO requestRankDTO, List<ResponseMountainRankDTO> responseMountainRankDTOs) {
        //메모. 처음 해당 산의 코스 도장을 받은 경우 산 등급 insert( 도장이 한개인 산은 바로 산신령 후보로 올림)
        if (requestRankDTO.isFirstClimbForMountain() && !requestRankDTO.isNewMountainStamp()) {
            MountainRank mountainRank = new MountainRank(
                    null, 1L, requestRankDTO.getCumId(), requestRankDTO.getFrtrlId()
            );
            MountainRank myMountainRank = mountainsRankRepository.save(mountainRank);

            addResponseRankDataOfMountainRank(myMountainRank, responseMountainRankDTOs, "me");
        } else {
            checkHighestClimber(requestRankDTO, responseMountainRankDTOs);
        }
    }

    private void checkHighestClimber(RequestRankDTO requestRankDTO
            , List<ResponseMountainRankDTO> responseMountainRankDTOs) {

        //나의 랭크
        MountainRank myRank = mountainsRankRepository.findByCumIdAndFrtrlId(requestRankDTO.getCumId(),requestRankDTO.getFrtrlId());
        if( myRank == null) {
            myRank = new  MountainRank();
            myRank.setCumId( requestRankDTO.getCumId());
            myRank.setFrtrlId( requestRankDTO.getFrtrlId());
            myRank.setMtRankId(1L);
        }

        // 가장 높은 등급을 조회
        long maxRank = mountainRankStandardRepository.findAll()
                .stream()
                .mapToLong(x -> x.getId())
                .max()
                .orElse(0L);

        //메모. 현재 산에서 산 도장이 있는 회원 전체 조회(가장 많은 등산 횟 수를 가진 사람을 찾기 위 함)
        List<MountainRank> mountainRanks = mountainsRankRepository.findByFrtrlIdAndMtRankIdGreaterThanEqual(
                requestRankDTO.getFrtrlId(), maxRank
        );
        //메모. 최초 해당 산의 산도장을 획득 했을 경우 바로 산신령 등급으로 랭크 업
        if (requestRankDTO.isNewMountainStamp() && mountainRanks.isEmpty()) {
            MountainRank mountainRank = mountainsRankRepository.findByCumIdAndFrtrlId(requestRankDTO.getCumId(),requestRankDTO.getFrtrlId());
            mountainRank = mountainRank == null ? myRank : mountainRank;

            //산신령으로 등급 조정
            mountainRank.setMtRankId(maxRank);
            MountainRank myMountainRank = mountainsRankRepository.save(mountainRank);

            addResponseRankDataOfMountainRank(myMountainRank, responseMountainRankDTOs, "me");

        } else if (requestRankDTO.isNewMountainStamp() && !mountainRanks.isEmpty()
                || requestRankDTO.isAlreadyExistsMountainStamp()) {
            // 이미 산 도장 있거나, 최초 산도장이면서 이미 최고 등급(산신령) 이 있을 경우

            //메모. query로 산 도장이 있고 등산 횟수가 가장 많은 회원 2명 조회
            List<HigherClimber> higherClimbers =
                    memberRankQueryService.selectLotsOfClimbingByMountain(requestRankDTO.getFrtrlId());

            // 산 도장이 있고 내가 가장 많은 등산 횟수 보유자
            if ((higherClimbers.size() == 0 || higherClimbers.get(0).getCumId().equals(requestRankDTO.getCumId()))
            && myRank.getMtRankId() != maxRank) {

                myRank.setMtRankId(maxRank);
                //산신령 등급으로 등급 업
                MountainRank myMountainRank = mountainsRankRepository.save(myRank);

                addResponseRankDataOfMountainRank(myMountainRank, responseMountainRankDTOs, "me");

                //2번째 등산 횟수 나 보다 적은 등산 횟수 보유자는 다시 아래 단계로 등급 하락
                if (higherClimbers.size() > 1 && higherClimbers.get(1).getMtRankId() == maxRank) {

                    //산신령 등급에서 아래 단계로 등급 하락
                    MountainRank previousHighestRank
                            = mountainsRankRepository.findByCumIdAndFrtrlId(higherClimbers.get(1).getCumId(),higherClimbers.get(1).getFrtrlId());
                    previousHighestRank.setMtRankId(maxRank - 1);
                    MountainRank previousRank = mountainsRankRepository.save(previousHighestRank);

                    addResponseRankDataOfMountainRank(previousRank, responseMountainRankDTOs, "previous");
                }
            } else if(requestRankDTO.isNewMountainStamp()){
                // 봉우리 마스터로 등급 변경
                myRank.setMtRankId(2L);
                MountainRank myMountainRank = mountainsRankRepository.save(myRank);
                addResponseRankDataOfMountainRank(myMountainRank, responseMountainRankDTOs, "me");
            }
        }

    }

    // 등급 변경 정보를 프론트에 뿌리기위해 보낼 데이터 저장
    private void addResponseRankDataOfMountainRank(MountainRank mountainRank
            , List<ResponseMountainRankDTO> responseMountainRankDTOs
            , String memberType) {
        ResponseMountainRankDTO responseMyRank = modelMapper.map(mountainRank, ResponseMountainRankDTO.class);

        //등급 코드의 이름 을 가지고 오기위함 1: 산길 탐험가, 2: 봉우리마스터, 3: 산신령
        List<MountainRankStandard> mountainRankStandards = mountainRankStandardRepository.findAll();
        MountainRankStandard mountainRankStandard = mountainRankStandards.stream()
                .filter(x -> x.getId().equals(responseMyRank.getMtRankId()))
                .findFirst()
                .orElse(null);
        if (mountainRankStandard != null) {
            responseMyRank.setMtRankName(mountainRankStandard.getRankName());
        }

        responseMyRank.setMemberType(memberType);
        responseMountainRankDTOs.add(responseMyRank);
    }

}
