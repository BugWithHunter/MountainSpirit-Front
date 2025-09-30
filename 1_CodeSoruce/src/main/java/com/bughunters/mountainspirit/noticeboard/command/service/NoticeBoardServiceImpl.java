package com.bughunters.mountainspirit.noticeboard.command.service;

import com.bughunters.mountainspirit.noticeboard.command.dto.BoardDTO;
import com.bughunters.mountainspirit.noticeboard.command.dto.BoardModifyDTO;
import com.bughunters.mountainspirit.noticeboard.command.entity.NoticeBoard;
import com.bughunters.mountainspirit.noticeboard.command.entity.NoticeBoardImage;
import com.bughunters.mountainspirit.noticeboard.command.repository.NoticeBoardImageRepository;
import com.bughunters.mountainspirit.noticeboard.command.repository.NoticeBoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class NoticeBoardServiceImpl implements NoticeBoardService {
    private final ModelMapper modelMapper;
    NoticeBoardRepository noticeBoardRepository;
    NoticeBoardImageRepository noticeBoardImageRepository;

    @Autowired
    public NoticeBoardServiceImpl(ModelMapper modelMapper,
                                  NoticeBoardRepository noticeBoardRepository,
                                  NoticeBoardImageRepository noticeBoardImageRepository) {
        this.modelMapper = modelMapper;
        this.noticeBoardRepository = noticeBoardRepository;
        this.noticeBoardImageRepository = noticeBoardImageRepository;
    }

    @Value("${filepath}")
    private String filepath;

    @Override
    @Transactional
    public void registPost(BoardDTO boardDTO, List<MultipartFile> multiFiles) {
        boardDTO.setCumId(199);    // @Authentication으로 회원 인식하면 이 라인 지울것!
        boardDTO.setIsDeleted("N");
        boardDTO.setCreateDate(LocalDateTime.now());

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        NoticeBoard noticeBoard = modelMapper.map(boardDTO, NoticeBoard.class);
        noticeBoardRepository.saveAndFlush(noticeBoard);

        List<Map<String, Object>> files = multiFileUpload(multiFiles, noticeBoard);
        insertInBoardImageEntity(files);

    }

    @Override
    @Transactional
    public void modifyPostById(BoardModifyDTO modifyPost, int id) {

        NoticeBoard noticeBoard = noticeBoardRepository.findById(id).get();

        modifyBoard(modifyPost, noticeBoard);
    }

    @Override
    @Transactional
    public void addImageInPost(List<MultipartFile> multiFiles, int postid) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(postid).get();
        List<Map<String, Object>> files = multiFileUpload(multiFiles, noticeBoard);
        insertInBoardImageEntity(files);
    }

    @Override
    @Transactional
    public void deleteBoardImage(long id) {
        noticeBoardImageRepository.delete(noticeBoardImageRepository.findById(id).get());
    }

    @Override
    @Transactional
    public void deletePostById(int id) {
        NoticeBoard noticeBoard = noticeBoardRepository.findById(id).get();

        noticeBoard.setIsDeleted("Y");
        noticeBoardRepository.save(noticeBoard);
    }


    /* 설명. class 내부 구현 메소드 */
    private List<Map<String, Object>> multiFileUpload(List<MultipartFile> multiFiles, NoticeBoard noticeBoard) {

        if (multiFiles == null) {
            multiFiles = new ArrayList<>();
        }

        /* 설명. DB에 보낼 값을 담기 위한 컬렉션 */
        List<Map<String, Object>> files = new ArrayList<>();

        /* 설명. 화면에서 각 파일마다 img 태그의 src 속성으로 적용하기 위한 문자열을 담은 컬렉션 */
        List<String> imgSrcs = new ArrayList<>();

        try {
            for (int i = 0; i < multiFiles.size(); i++) {

                /* 설명. 각 파일마다 리네임 */
                String originFileName = multiFiles.get(i).getOriginalFilename();
                String ext = originFileName.substring(originFileName.lastIndexOf("."));
                String saveName = UUID.randomUUID().toString().replace("-", "") + ext;

                /* 설명. 각 파일을 저장 경로에 저장 */
                multiFiles.get(i).transferTo(new File(filepath + "/img/multi/" + saveName));

                /* 설명. DB에 보낼 값 설정(각 파일마다 Map<String, String>에 저장) */
                Map<String, Object> file = new HashMap<>();
                file.put("originName", originFileName);
                file.put("renaming", saveName);
                file.put("thumbnail", "/img/multi/" + saveName);
                file.put("path", "/img/multi/");
                file.put("postId", noticeBoard.getId());


                files.add(file);
                imgSrcs.add("/img/multi/" + saveName);
            }


        } catch (IOException e) {

            /* 설명. 부분적인 파일 저장 실패와 관련되어 후처리 */
            for (int i = 0; i < files.size(); i++) {       // 업로드에 성공한 것들은 List에 쌓였다는 생각으로
                Map<String, Object> file = files.get(i);
                new File(filepath + "/img/multi/" + file.get("saveName")).delete();
            }

        }
        return files;
    }

    private void insertInBoardImageEntity(List<Map<String, Object>> files) {

        for (Map<String, Object> file : files) {
            NoticeBoardImage noticeBoardImage = new NoticeBoardImage();
            noticeBoardImage.setOriginName((String) file.get("originName"));
            noticeBoardImage.setRenaming((String) file.get("renaming"));
            noticeBoardImage.setThumbnail((String) file.get("thumbnail"));
            noticeBoardImage.setPath((String) file.get("path"));
            noticeBoardImage.setPostId((Long) file.get("postId"));

            noticeBoardImageRepository.save(noticeBoardImage);
        }

    }

    private void modifyBoard(BoardModifyDTO modifyPost, NoticeBoard noticeBoard) {
        if(!noticeBoard.getTitle().equals(modifyPost.getTitle())) {
            noticeBoard.setTitle(modifyPost.getTitle());
        }
        if(!noticeBoard.getContent().equals(modifyPost.getContent())) {
            noticeBoard.setContent(modifyPost.getContent());
        }

        noticeBoard.setModifyDate(LocalDateTime.now());

        noticeBoardRepository.save(noticeBoard);
    }
}
