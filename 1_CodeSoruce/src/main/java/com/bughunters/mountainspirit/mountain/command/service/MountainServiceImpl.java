package com.bughunters.mountainspirit.mountain.command.service;

import com.bughunters.mountainspirit.mountain.command.dto.ResponseApiMountainDTO;
import com.bughunters.mountainspirit.mountain.command.dto.ResponseApiPeakDTO;
import com.bughunters.mountainspirit.mountain.command.entity.Course;
import com.bughunters.mountainspirit.mountain.command.entity.Mountain;
import com.bughunters.mountainspirit.mountain.command.repository.CourseRepository;
import com.bughunters.mountainspirit.mountain.command.repository.MountainRepository;
import com.bughunters.mountainspirit.mountain.command.dto.ResponseCourseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MountainServiceImpl implements MountainService {

    private ModelMapper modelMapper;
    private MountainRepository mountainRepository;
    private CourseRepository courseRepository;
    private HttpUtil httpUtil;

    @Value("${govermentData.path.peakPoint}")
    private String peakPath;
    @Value("${govermentData.path.mountain}")
    private String mountainPath;

    @Autowired
    public MountainServiceImpl(ModelMapper modelMapper,
                               MountainRepository mountainRepository,
                               CourseRepository courseRepository,
                               HttpUtil httpUtil) {
        this.modelMapper = modelMapper;
        this.mountainRepository = mountainRepository;
        this.courseRepository = courseRepository;
        this.httpUtil = httpUtil;
    }


    @Transactional
    public void mergeAllMountains() {
        final int size = 100;
        int total = httpUtil.fetchTotalCount(mountainPath);  //데이터 총 개수 조회
        int pages = (total + size - 1) / size;

        mountainRepository.findAllCourses();
        for (int i = 1; i <= pages; i++) {
            List<ResponseApiMountainDTO> items = httpUtil.fetchItems(i, size, mountainPath, ResponseApiMountainDTO.class); // 100개 단위로 데이터 조회

            mergeData(items, Mountain.class, mountainRepository);
        }
    }

    public void mergeAllCourses() {
        final int size = 100;
        int total = httpUtil.fetchTotalCount(peakPath);  //데이터 총 개수 조회
        int pages = (total + size - 1) / size;
        courseRepository.findAllCourses();
        for (int i = 1; i <= pages; i++) {
            List<ResponseApiPeakDTO> items = httpUtil.fetchItems(i, size, peakPath, ResponseApiPeakDTO.class); // 100개 단위로 데이터 조회
            mergeData(items, Course.class, courseRepository);
        }
    }

    @Override
    public List<ResponseCourseDTO> selectCoursesByPoiId(String frtrlId) {
        List<Course> courses = courseRepository.findAllByFrtrlId(frtrlId);
        List<ResponseCourseDTO> courseDTOs = new ArrayList<>();
        for(Course course : courses){
            ResponseCourseDTO dto = modelMapper.map(course, ResponseCourseDTO.class);
            courseDTOs.add(dto);
        }
        System.out.println("courseDTOs = " + courseDTOs);
        return courseDTOs;
    }

    public <S, D> void mergeData(List<S> list, Class<D> destType, JpaRepository<D, ?> repo) {
        List<D> entities = list.stream()
                .map(item -> modelMapper.map(item, destType))
                .collect(Collectors.toList());
        repo.saveAll(entities);
    }

    @Override
    public String getMountainName(String frtrlId) {
        return mountainRepository.findNameById(frtrlId);
    }

}
