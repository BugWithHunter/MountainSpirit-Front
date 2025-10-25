package com.bughunters.mountainspirit.member.command.service;

import com.bughunters.mountainspirit.member.command.dto.ResponseProfileImageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ProfileImageService {

    private String uploadDir = "";

    public ProfileImageService(@Value("${profile.dirPath}") String dirPath) {
        //System.getProperty("user.dir") 실행 중인 프로젝트 루트 경로
        uploadDir = System.getProperty("user.dir").replace("\\","/") + dirPath;
    }

    public ResponseProfileImageDTO updateProfileImage(MultipartFile singleFile, Long id) throws IOException {
        String originFileName = singleFile.getOriginalFilename();
        String ext = originFileName.substring(originFileName.lastIndexOf("."));
        String saveName = id + ext;
        String savePath = uploadDir + saveName;
        // 폴더가 경로상에 없을 경우 생성
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        singleFile.transferTo(new File(savePath));
        ResponseProfileImageDTO responseProfileImageDTO = new ResponseProfileImageDTO(id, uploadDir, savePath, true,"");

        return responseProfileImageDTO;
    }
}
