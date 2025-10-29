package com.bughunters.mountainspirit.mountainbookmark.query.service;

import com.bughunters.mountainspirit.mountainbookmark.query.dto.CourseBookmarkDTO;
import com.bughunters.mountainspirit.mountainbookmark.query.dto.MountainBookmarkDTO;
import com.bughunters.mountainspirit.mountainbookmark.query.mapper.CourseBookmarkGetMapper;
import com.bughunters.mountainspirit.mountainbookmark.query.mapper.MountainBookmarkGetMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkServiceImpl implements BookmarkService{
    private MountainBookmarkGetMapper mountainBookmarkGetMapper;
    private CourseBookmarkGetMapper courseBookmarkGetMapper;
    private final SqlSessionTemplate sqlSession;

    @Autowired
    public BookmarkServiceImpl(MountainBookmarkGetMapper mountainBookmarkGetMapper,
                               CourseBookmarkGetMapper courseBookmarkGetMapper, SqlSessionTemplate sqlSession) {
        this.mountainBookmarkGetMapper = mountainBookmarkGetMapper;
        this.courseBookmarkGetMapper = courseBookmarkGetMapper;
        this.sqlSession = sqlSession;
    }

    @Override
    public List<MountainBookmarkDTO> getMountainBookmark(Long memberId) {
        List<MountainBookmarkDTO> mountainBookmarkList = mountainBookmarkGetMapper.selectMountainBookmarkById(memberId);
        return mountainBookmarkList;
    }

    @Override
    public List<CourseBookmarkDTO> getCourseBookmark(Long memberId) {
        List<CourseBookmarkDTO> courseBookmarkList = courseBookmarkGetMapper.selectCourseBookmarkById(memberId);
        return courseBookmarkList;
    }
}
