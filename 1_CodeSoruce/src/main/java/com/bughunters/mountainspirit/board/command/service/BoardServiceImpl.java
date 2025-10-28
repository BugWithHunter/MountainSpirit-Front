package com.bughunters.mountainspirit.board.command.service;

import com.bughunters.mountainspirit.board.command.dto.BoardDTO;
import com.bughunters.mountainspirit.board.command.dto.BoardModifyDTO;
import com.bughunters.mountainspirit.board.command.dto.LikesDTO;
import com.bughunters.mountainspirit.board.command.entity.Board;
import com.bughunters.mountainspirit.board.command.entity.BoardImage;
import com.bughunters.mountainspirit.board.command.entity.Likes;
import com.bughunters.mountainspirit.board.command.repository.BoardImageRepository;
import com.bughunters.mountainspirit.board.command.repository.BoardRepository;
import com.bughunters.mountainspirit.board.command.repository.LikesRepository;
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
public class BoardServiceImpl implements BoardService {
    private final ModelMapper modelMapper;
    BoardRepository boardRepository;
    BoardImageRepository boardImageRepository;
    LikesRepository likesRepository;

    @Autowired
    public BoardServiceImpl(ModelMapper modelMapper,
                            BoardRepository boardRepository,
                            BoardImageRepository boardImageRepository,
                            LikesRepository likesRepository) {
        this.modelMapper = modelMapper;
        this.boardRepository = boardRepository;
        this.boardImageRepository = boardImageRepository;
        this.likesRepository = likesRepository;
    }

    @Value("${filepath}")
    private String filepath;


    @Override
    @Transactional
    public void registPost(BoardDTO boardDTO, List<MultipartFile> multiFiles) {
        boardDTO.setIsDeleted("N");
        boardDTO.setCreateDate(LocalDateTime.now());

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Board board = modelMapper.map(boardDTO, Board.class);
//        boardRepository.save(board);
        log.info("save 전 게시글ID : {}", board.getId());
        try {
            boardRepository.saveAndFlush(board);
            log.info("After saveAndFlush, board ID: {}", board.getId());
        } catch (Exception e) {
            log.error("Error saving board entity", e);
            throw e; // 필요시 다시 던지기
        }

        boardRepository.saveAndFlush(board);
        log.info("save 후 게시글ID : {}", board.getId());


        List<Map<String, Object>> files = multiFileUpload(multiFiles, board);
        insertInBoardImageEntity(files);
    }

    @Override
    @Transactional
    public void modifyPostById(BoardModifyDTO modifyPost, int id) {
        Board board = boardRepository.findById(id).get();

        modifyBoard(modifyPost, board);
    }

    @Override
    @Transactional
    public void addImageInPost(List<MultipartFile> multiFiles, int postid) {
        Board board = boardRepository.findById(postid).get();
        List<Map<String, Object>> files = multiFileUpload(multiFiles, board);
        insertInBoardImageEntity(files);
    }

    @Override
    @Transactional
    public void deleteBoardImage(long id) {
        boardImageRepository.delete(boardImageRepository.findById(id).get());
    }

    @Override
    @Transactional
    public void deletePostById(int id) {
        Board board = boardRepository.findById(id).get();

        board.setIsDeleted("Y");
        boardRepository.save(board);
    }


    /* 필기. 좋아요를 누른 회웡ID는 service계층에서 @Authentication을 이용해서 받아오면 되는지? */
    @Override
    @Transactional
    public void createOrDeleteLikesByPostId(int id, long userId) {
        Board board = boardRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        Likes likes = likesRepository.findByPostId(board.getId());
        if (likes != null && likes.getCumId() == userId) {
            likesRepository.delete(likes);
        } else {

            LikesDTO likesDTO = new LikesDTO();
            likesDTO.setPostId((long)id);
            likesDTO.setCumId(userId);

            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            likes = modelMapper.map(likesDTO, Likes.class);

            likesRepository.save(likes);
        }
    }


    /* 설명. class 내부 구현 메소드 */
    private List<Map<String, Object>> multiFileUpload(List<MultipartFile> multiFiles, Board board) {

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
                file.put("postId", board.getId());


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
            BoardImage boardImage = new BoardImage();
            boardImage.setOriginName((String) file.get("originName"));
            boardImage.setRenaming((String) file.get("renaming"));
            boardImage.setThumbnail((String) file.get("thumbnail"));
            boardImage.setPath((String) file.get("path"));
            boardImage.setPostId((Long) file.get("postId"));

            log.info("save 전 게시글이미지ID : {}", boardImage.getId());
            boardImageRepository.saveAndFlush(boardImage);
            log.info("save 후 게시글이미지ID : {}", boardImage.getId());
        }

    }


    private void modifyBoard(BoardModifyDTO modifyPost, Board board) {
        if(modifyPost.getTitle() != null && !board.getTitle().equals(modifyPost.getTitle())) {
            board.setTitle(modifyPost.getTitle());
        }
        if(modifyPost.getContent() != null && !board.getContent().equals(modifyPost.getContent())) {
            board.setContent(modifyPost.getContent());
        }

        board.setModifyDate(LocalDateTime.now());

        boardRepository.save(board);
    }
}
