package com.bughunters.mountainspirit.stamp.command.service;

import com.bughunters.mountainspirit.climbhistory.command.dto.RequestStartClimbMountainDTO;
import com.bughunters.mountainspirit.mountain.command.service.MountainService;
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
    public StampWithCourseAndMountainDTO copleteClimbingMountain(RequestStartClimbMountainDTO request) {

        //메모. 해당 산의 전체 경로 정보 조회
        List<> mountainService.select

        //메모. 현재 회원이 해당산의 가지고 있는전체 도장 조회
        List<CourseStamp> courseStamps = courseStampRepository.findByCumId(request.getCumId());

        //메모. 현재 경로의 도장이 있는지 확인
        CourseStamp courseStamp = courseStamps.stream()
                .filter(x -> x.getPoiId().equals(request.getPoiId()))
                .findFirst()
                .orElse(null);

        //메모. 해당 코스 최초 등산으로 도장 흭득
        if (courseStamp == null) {
            CourseStamp newCourseStamp = new CourseStamp(
                    null, LocalDateTime.now(), request.getPoiId(), request.getCumId()
            );
            courseStampRepository.save(newCourseStamp);
        }

        System.out.println("courseStamps = " + courseStamps);
//        CourseStamp courseStamp = courseStampRepository.findById(Long.parseLong(request.getPoiId())).orElse(null);
        MountainStamp mountainStamp = mountainStampRepository.findById(Long.parseLong(request.getFrtrlId())).orElse(null);


        return null;
    }


}
