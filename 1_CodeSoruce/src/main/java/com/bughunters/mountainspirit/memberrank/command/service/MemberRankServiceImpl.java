package com.bughunters.mountainspirit.memberrank.command.service;

import com.bughunters.mountainspirit.memberrank.command.dto.RequestRankDTO;
import com.bughunters.mountainspirit.memberrank.command.dto.ResponseMountainRankDTO;
import com.bughunters.mountainspirit.memberrank.command.dto.ResponseRankDTO;
import com.bughunters.mountainspirit.memberrank.command.entity.MountainRank;
import com.bughunters.mountainspirit.memberrank.command.repository.MemberRankRepository;
import com.bughunters.mountainspirit.memberrank.command.repository.MountainRankRepository;
import com.bughunters.mountainspirit.memberrank.command.repository.MountainRankStandardRepository;
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
    private final ModelMapper modelMapper;

    public MemberRankServiceImpl(MountainRankRepository mountainsRankRepository
            , MemberRankRepository memberRankRepository
            , MountainRankStandardRepository mountainRankStandardRepository
            , MemberRankQueryService memberRankQueryService
            , ModelMapper modelMapper) {
        this.mountainsRankRepository = mountainsRankRepository;
        this.memberRankRepository = memberRankRepository;
        this.mountainRankStandardRepository = mountainRankStandardRepository;
        this.memberRankQueryService = memberRankQueryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseRankDTO modifyMemberRank(RequestRankDTO requestRankDTO) {

        ResponseRankDTO responseRankDTO = new ResponseRankDTO();
        //응답으로 전송 할 데이터(내 등급, 이전 산신령이 있었을 경우 이전 산신령 정보)
        List<ResponseMountainRankDTO> responseMountainRankDTOs = new ArrayList<>();

        //메모. 처음 해당 산의 코스 도장을 받은 경우 산 등급 insert
        if (requestRankDTO.isFirstClimbForMountain()) {
            MountainRank mountainRank = new MountainRank(
                    null, 1L, requestRankDTO.getCumId(), requestRankDTO.getFrtrlId()
            );
            MountainRank myMountainRank = mountainsRankRepository.save(mountainRank);

            addResponseRankDataOfMountainRank(myMountainRank, responseMountainRankDTOs, "me");
        } else {
            checkHighestClimber(requestRankDTO, responseMountainRankDTOs);
        }

        responseRankDTO.setModifyRanks(responseMountainRankDTOs);

        if (responseMountainRankDTOs.size() > 0) {
            responseRankDTO.setModifyMyMountainRank(true);
        }

        return responseRankDTO;
    }

    private void checkHighestClimber(RequestRankDTO requestRankDTO
            , List<ResponseMountainRankDTO> responseMountainRankDTOs) {

        //나의 랭크
        MountainRank myRank = mountainsRankRepository.findByCumId(requestRankDTO.getCumId());

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
            MountainRank mountainRank = mountainsRankRepository.findByCumId(requestRankDTO.getCumId());

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
            if (higherClimbers.get(0).getCumId().equals(requestRankDTO.getCumId())) {

                myRank.setMtRankId(maxRank);
                //산신령 등급으로 등급 업
                MountainRank myMountainRank = mountainsRankRepository.save(myRank);

                addResponseRankDataOfMountainRank(myMountainRank, responseMountainRankDTOs, "me");

                //2번째 등산 횟수 나 보다 적은 등산 횟수 보유자는 다시 아래 단계로 등급 하락
                if (higherClimbers.size() > 1 && higherClimbers.get(1).getMtRankId() == maxRank) {

                    //산신령 등급에서 아래 단계로 등급 하락
                    MountainRank previousHighestRank
                            = mountainsRankRepository.findByCumId(higherClimbers.get(1).getCumId());
                    previousHighestRank.setMtRankId(maxRank - 1);
                    MountainRank previousRank = mountainsRankRepository.save(previousHighestRank);

                    addResponseRankDataOfMountainRank(previousRank, responseMountainRankDTOs, "previous");
                }
            }


        }
    }

    // 등급 변경 정보를 프론트에 뿌리기위해 보낼 데이터 저장
    private void addResponseRankDataOfMountainRank(MountainRank mountainRank
            , List<ResponseMountainRankDTO> responseMountainRankDTOs
            , String memberType) {
        ResponseMountainRankDTO responseMyRank = modelMapper.map(mountainRank, ResponseMountainRankDTO.class);
        responseMyRank.setMemberType(memberType);
        responseMountainRankDTOs.add(responseMyRank);
    }

}
