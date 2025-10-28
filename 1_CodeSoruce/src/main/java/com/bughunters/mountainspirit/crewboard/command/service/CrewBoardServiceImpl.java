package com.bughunters.mountainspirit.crewboard.command.service;

import com.bughunters.mountainspirit.board.command.entity.Likes;
import com.bughunters.mountainspirit.crewboard.command.dto.BoardDTO;
import com.bughunters.mountainspirit.crewboard.command.dto.BoardModifyDTO;
import com.bughunters.mountainspirit.crewboard.command.dto.LikesDTO;
import com.bughunters.mountainspirit.crewboard.command.entity.CrewBoard;
import com.bughunters.mountainspirit.crewboard.command.entity.CrewBoardImage;
import com.bughunters.mountainspirit.crewboard.command.repository.CrewBoardImageRepository;
import com.bughunters.mountainspirit.crewboard.command.repository.CrewBoardRepository;
import com.bughunters.mountainspirit.crewboard.command.repository.CrewLikesRepository;
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
public class CrewBoardServiceImpl implements CrewBoardService {
    private final ModelMapper modelMapper;
    CrewBoardRepository crewBoardRepository;
    CrewBoardImageRepository crewBoardImageRepository;
    CrewLikesRepository crewLikesRepository;

    @Autowired
    public CrewBoardServiceImpl(ModelMapper modelMapper,
                                CrewBoardRepository crewBoardRepository,
                                CrewBoardImageRepository crewBoardImageRepository,
                                CrewLikesRepository crewLikesRepository) {
        this.modelMapper = modelMapper;
        this.crewBoardRepository = crewBoardRepository;
        this.crewBoardImageRepository = crewBoardImageRepository;
        this.crewLikesRepository = crewLikesRepository;
    }
    @Value("${filepath}")
    private String filepath;

    @Override
    @Transactional
    public void registPost(BoardDTO boardDTO, List<MultipartFile> multiFiles) {
        boardDTO.setIsDeleted("N");
        boardDTO.setCreateDate(LocalDateTime.now());

        choosePostType(boardDTO);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CrewBoard crewBoard = modelMapper.map(boardDTO, CrewBoard.class);
        crewBoardRepository.save(crewBoard);

        List<Map<String, Object>> files = multiFileUpload(multiFiles, crewBoard);
        insertInBoardImageEntity(files);

    }

    @Override
    @Transactional
    public void modifyPostById(BoardModifyDTO modifyPost, int id) {

        CrewBoard crewBoard = crewBoardRepository.findById(id).get();


        modifyBoard(modifyPost, crewBoard);
    }

    @Override
    @Transactional
    public void addImageInPost(List<MultipartFile> multiFiles, int postid) {
        CrewBoard crewBoard = crewBoardRepository.findById(postid).get();
        List<Map<String, Object>> files = multiFileUpload(multiFiles, crewBoard);
        insertInBoardImageEntity(files);
    }

    @Override
    @Transactional
    public void deleteBoardImage(long id) {
        crewBoardImageRepository.delete(crewBoardImageRepository.findById(id).get());
    }

    @Override
    @Transactional
    public void deletePostById(int id) {
        CrewBoard crewBoard = crewBoardRepository.findById(id).get();

        crewBoard.setIsDeleted("Y");
        crewBoardRepository.save(crewBoard);
    }


    @Override
    @Transactional
    public void createOrDeleteLikesByPostId(int id, long userId) {
        CrewBoard crewBoard = crewBoardRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        Likes likes = crewLikesRepository.findByPostId(crewBoard.getId());
        if (likes != null && likes.getCumId() == userId) {
            crewLikesRepository.delete(likes);
        } else {

            LikesDTO likesDTO = new LikesDTO();
            likesDTO.setCrewPostId((long)id);
            likesDTO.setCumId(userId);

            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            likes = modelMapper.map(likesDTO, Likes.class);

            crewLikesRepository.save(likes);
        }
    }

    /* 설명. class 내부 구현 메소드 */
    private List<Map<String, Object>> multiFileUpload(List<MultipartFile> multiFiles, CrewBoard crewBoard) {

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
                file.put("postId", crewBoard.getId());


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

    private void choosePostType(BoardDTO boardDTO) {   // 크루장만 선택할 수 있는 권한이 있어야 함.
        if(boardDTO.getIsNotice().equals("N")) {
            boardDTO.setIsNotice("N");
        } else if (boardDTO.getIsNotice().equals("Y")) {
            boardDTO.setIsNotice("Y");
        }
    }

    private void insertInBoardImageEntity(List<Map<String, Object>> files) {

        for (Map<String, Object> file : files) {
            CrewBoardImage crewBoardImage = new CrewBoardImage();
            crewBoardImage.setOriginName((String) file.get("originName"));
            crewBoardImage.setRenaming((String) file.get("renaming"));
            crewBoardImage.setThumbnail((String) file.get("thumbnail"));
            crewBoardImage.setPath((String) file.get("path"));
            crewBoardImage.setPostId((Long) file.get("postId"));

            crewBoardImageRepository.save(crewBoardImage);
        }

    }

    private void modifyBoard(BoardModifyDTO modifyPost, CrewBoard crewBoard) {
        if(!crewBoard.getTitle().equals(modifyPost.getTitle())) {
            crewBoard.setTitle(modifyPost.getTitle());
        }
        if(!crewBoard.getContent().equals(modifyPost.getContent())) {
            crewBoard.setContent(modifyPost.getContent());
        }

        crewBoard.setModifyDate(LocalDateTime.now());

        crewBoardRepository.save(crewBoard);
    }
}
