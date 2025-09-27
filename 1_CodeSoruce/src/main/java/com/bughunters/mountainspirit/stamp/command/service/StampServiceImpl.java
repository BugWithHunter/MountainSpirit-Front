package com.bughunters.mountainspirit.stamp.command.service;

import com.bughunters.mountainspirit.mountain.command.service.MountainService;
import com.bughunters.mountainspirit.stamp.command.dto.RequestSubmmitClimbMountainDTO;
import com.bughunters.mountainspirit.stamp.command.dto.StampWithCourseAndMountainDTO;
import com.bughunters.mountainspirit.stamp.command.entity.CourseStamp;
import com.bughunters.mountainspirit.stamp.command.entity.MountainStamp;
import com.bughunters.mountainspirit.stamp.command.repository.CourseStampRepository;
import com.bughunters.mountainspirit.stamp.command.repository.MountainStampRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StampServiceImpl implements StampService {

    private final CourseStampRepository courseStampRepository;
    private final MountainStampRepository mountainStampRepository;
    private final MountainService mountainService;

    public StampServiceImpl(CourseStampRepository courseStampRepository
            , MountainStampRepository mountainStampRepository
            , MountainService mountainService) {
        this.courseStampRepository = courseStampRepository;
        this.mountainStampRepository = mountainStampRepository;
        this.mountainService = mountainService;
    }

    @Override
    @Transactional
    public StampWithCourseAndMountainDTO copleteClimbingMountain(RequestSubmmitClimbMountainDTO request) {

        StampWithCourseAndMountainDTO stampDTO = new StampWithCourseAndMountainDTO();

        //메모. 해당 산의 전체 경로 정보 조회
        List<com.bughunters.mountainspirit.mountain.command.dto.ResponseCourseDTO> courses
                = mountainService.selectCoursesByPoiId(request.getFrtrlId());

        //메모. 현재 회원이 해당산의 가지고 있는전체 도장 조회
        List<CourseStamp> courseStamps = courseStampRepository.findByCumId(request.getCumId());

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

            //메모. 해당산의 최초 코스 도장 흭득하여 산별 회원 등급에 등급 테이블에 insert
            if(courseStamps.size() == 0){

            }


            stampDTO.setNewCourseStamp(true);

            //메모. 코스 도장 추가
            courseStampRepository.save(new CourseStamp(
                    null, LocalDateTime.now(), request.getPoiId(), request.getCumId()));

            //메모. 해당산의 모든 코스 수 == 회원의 기존 도장수 + 추가 도장 수가 같으면 모든 도장을 모아 산도장 흭득
            if(courses.size() == courseStamps.size()) {
                stampDTO.setNewMountainStamp(true);

                //메모. 산 도장 추가
                mountainStampRepository.save(new MountainStamp(
                        null,  LocalDateTime.now(), request.getCumId(), request.getFrtrlId()));
            }
        }

        System.out.println("courseStamps = " + courseStamps);
        MountainStamp mountainStamp = mountainStampRepository.findById(Long.parseLong(request.getFrtrlId())).orElse(null);

        return null;
    }

}
