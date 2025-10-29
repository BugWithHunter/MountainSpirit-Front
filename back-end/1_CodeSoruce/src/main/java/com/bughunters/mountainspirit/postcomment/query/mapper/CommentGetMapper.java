package com.bughunters.mountainspirit.postcomment.query.mapper;

import com.bughunters.mountainspirit.postcomment.query.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentGetMapper {
    List<CommentDTO> selectCommentByPostId(int id);
}
