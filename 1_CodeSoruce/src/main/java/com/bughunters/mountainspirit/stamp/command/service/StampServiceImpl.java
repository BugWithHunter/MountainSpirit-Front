package com.bughunters.mountainspirit.stamp.command.service;

import com.bughunters.mountainspirit.stamp.command.dto.ResponseRankDTO;
import com.bughunters.mountainspirit.memberrank.command.service.MemberRankService;
import com.bughunters.mountainspirit.mountain.command.service.MountainService;
import com.bughunters.mountainspirit.memberrank.command.dto.RequestRankDTO;
import com.bughunters.mountainspirit.stamp.command.dto.RequestSubmmitClimbMountainDTO;
import com.bughunters.mountainspirit.stamp.command.dto.StampWithCourseAndMountainDTO;
import com.bughunters.mountainspirit.stamp.command.entity.CourseStamp;
import com.bughunters.mountainspirit.stamp.command.entity.MountainStamp;
import com.bughunters.mountainspirit.stamp.command.repository.CourseStampRepository;
import com.bughunters.mountainspirit.stamp.command.repository.MountainStampRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StampServiceImpl implements StampService {

    private final CourseStampRepository courseStampRepository;
    private final MountainStampRepository mountainStampRepository;
    private final MountainService mountainService;
    private final MemberRankService memberRankService;
    private final ModelMapper modelMapper;

    public StampServiceImpl(CourseStampRepository courseStampRepository
            , MountainStampRepository mountainStampRepository
            , MountainService mountainService
            , MemberRankService memberRankService
            , ModelMapper modelMapper) {
        this.courseStampRepository = courseStampRepository;
        this.mountainStampRepository = mountainStampRepository;
        this.mountainService = mountainService;
        this.memberRankService = memberRankService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public StampWithCourseAndMountainDTO copleteClimbingMountain(RequestSubmmitClimbMountainDTO request) {

        StampWithCourseAndMountainDTO stampDTO
                = modelMapper.map(request, StampWithCourseAndMountainDTO.class);
        
        //등급 변경을 위해 memberrank 패키지로 넘길 DTO
//        RequestRankDTO requestRankDTO = modelMapper.map(request,RequestRankDTO.class);

        //메모. 해당 산의 전체 경로 정보 조회
        List<com.bughunters.mountainspirit.mountain.command.dto.ResponseCourseDTO> courses
                = mountainService.selectCoursesByPoiId(request.getFrtrlId());

        //메모. 현재 회원이 해당산의 가지고 있는전체 도장 조회
        List<CourseStamp> courseStamps = courseStampRepository.findByCumId(request.getCumId());

        //해당 산의 처음 최초 등산 인지 확인
        if(courseStamps.isEmpty()){
//            requestRankDTO.setFirstClimbForMountain(true);
            stampDTO.setFirstClimbForMountain(true);
        }

        MountainStamp mountainStamp = mountainStampRepository.findByCumId(request.getCumId());

        // 원래 산 도장이 있던 회원인지 조회
        if(mountainStamp != null){
//            requestRankDTO.setAlreadyExistsMountainStamp(true);
            stampDTO.setAlreadyExistsMountainStamp(true);
        }

        //메모. 현재 경로의 도장이 있는지 확인
        CourseStamp courseStamp = courseStamps.stream()
                .filter(x -> x.getPoiId().equals(request.getPoiId()))
                .findFirst()
                .orElse(null);

        //메모. 코스 목록에서 해당 코스의 정보 검색(코스 난이도를 알기 위함)
        com.bughunters.mountainspirit.mountain.command.dto.ResponseCourseDTO course
                = courses.stream()
                .filter(x -> x.getPoiId().equals(request.getPoiId()))
                .findFirst()
                .orElse(null);

        // requestDTO에 코스 난이도 정보 저장
        stampDTO.setCourseRank(
                course == null ? 0 : course.getRankId()
        );

        //메모. 해당 코스 최초 등산으로 도장 흭득
        if (courseStamp == null) {
            stampDTO.setNewCourseStamp(true);
//            requestRankDTO.setNewCourseStamp(true);

            //메모. 코스 도장 추가
            courseStampRepository.save(new CourseStamp(
                    null, LocalDateTime.now(), request.getPoiId(), request.getCumId()));

            //메모. 해당산의 모든 코스 수 == 회원의 기존 도장수 + 추가 도장 수가 같으면 모든 도장을 모아 산도장 흭득
            if (courses.size() == courseStamps.size() + 1) {
                stampDTO.setNewMountainStamp(true);
//                requestRankDTO.setNewMountainStamp(true);

                if(mountainStampRepository.findByCumId(request.getCumId()) == null){
                    //메모. 산 도장 추가
                    mountainStampRepository.save(new MountainStamp(
                            null, LocalDateTime.now(), request.getCumId(), request.getFrtrlId()));
                }
            }
        }

//        //메모. 등급 변경 조건 확인 하고 등급 변경
//        com.bughunters.mountainspirit.memberrank.command.dto.ResponseRankDTO responseRankDTO
//                = memberRankService.modifyMemberRank(requestRankDTO);
//
//        //Stamp패키지에 있는 맵퍼로 변환
//        ResponseRankDTO rankDTO = modelMapper.map(responseRankDTO, ResponseRankDTO.class);
//        stampDTO.setResponseRankDTO(rankDTO);

        return stampDTO;
    }


}
