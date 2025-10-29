package com.bughunters.mountainspirit.stamp.command.service;

import com.bughunters.mountainspirit.memberrank.command.service.MemberRankService;
import com.bughunters.mountainspirit.mountain.command.service.MountainService;
import com.bughunters.mountainspirit.stamp.command.dto.RequestSubmmitClimbMountainDTO;
import com.bughunters.mountainspirit.stamp.command.dto.ResponseCourseDTO;
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
    private final ModelMapper modelMapper;

    public StampServiceImpl(CourseStampRepository courseStampRepository
            , MountainStampRepository mountainStampRepository
            , MountainService mountainService
            , ModelMapper modelMapper) {
        this.courseStampRepository = courseStampRepository;
        this.mountainStampRepository = mountainStampRepository;
        this.mountainService = mountainService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public StampWithCourseAndMountainDTO copleteClimbingMountain(RequestSubmmitClimbMountainDTO request) {

        StampWithCourseAndMountainDTO stampDTO
                = modelMapper.map(request, StampWithCourseAndMountainDTO.class);

        //메모. 해당 산의 전체 경로 정보 조회
        List<com.bughunters.mountainspirit.mountain.command.dto.ResponseCourseDTO> courses
                = mountainService.selectCoursesByPoiId(request.getFrtrlId());

        //메모. 현재 회원이 해당산의 가지고 있는전체 도장 조회
        List<CourseStamp> courseStamps = courseStampRepository.findByCumIdAndFrtrlId(request.getCumId() , request.getFrtrlId());

        //해당 산의 처음 최초 등산 인지 확인
        if(courseStamps.isEmpty()){
            stampDTO.setFirstClimbForMountain(true);
        }

        MountainStamp mountainStamp = mountainStampRepository.findByCumIdAndFrtrlId(request.getCumId(), request.getFrtrlId());

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

            //메모. 코스 도장 추가
            courseStampRepository.save(new CourseStamp(
                    null, LocalDateTime.now(), request.getPoiId(), request.getCumId() , request.getFrtrlId()));

            //메모. 해당산의 모든 코스 수 == 회원의 기존 도장수 + 추가 도장 수가 같으면 모든 도장을 모아 산도장 흭득
            if (courses.size() == courseStamps.size() + 1) {
                stampDTO.setNewMountainStamp(true);

                if(mountainStampRepository.findByCumIdAndFrtrlId(request.getCumId(), request.getFrtrlId()) == null){
                    //메모. 산 도장 추가
                    mountainStampRepository.save(new MountainStamp(
                            null, LocalDateTime.now(), request.getCumId(), request.getFrtrlId()));
                }
            }
        }


        return stampDTO;
    }

    @Override
    public List<CourseStamp> findCourseStamp(Long memberId) {
        //메모. 현재 회원이 해당산의 가지고 있는전체 도장 조회
        List<CourseStamp> courseStamps = courseStampRepository.findByCumId(memberId);

        return courseStamps;
    }


}
