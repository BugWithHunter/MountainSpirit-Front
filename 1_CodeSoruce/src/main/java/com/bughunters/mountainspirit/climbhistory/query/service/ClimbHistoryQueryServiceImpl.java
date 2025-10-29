package com.bughunters.mountainspirit.climbhistory.query.service;

import com.bughunters.mountainspirit.climbhistory.query.dto.*;
import com.bughunters.mountainspirit.climbhistory.query.mapper.ClimbHistoryQueryRepository;
import com.bughunters.mountainspirit.climbhistory.query.mapper.ClimbRecordQueryRepository;
import com.bughunters.mountainspirit.climbhistory.query.mapper.MountainRankQueryRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClimbHistoryQueryServiceImpl implements ClimbHistoryQueryService {

    private final ClimbHistoryQueryRepository climbHistoryQueryRepository;
    private final ClimbRecordQueryRepository climbRecordQueryRepository;
    private final MountainRankQueryRepository mountainRankQueryRepository;

    @Autowired
    public ClimbHistoryQueryServiceImpl(ClimbHistoryQueryRepository climbHistoryQueryRepository,
                                        ClimbRecordQueryRepository climbRecordQueryRepository,
                                        MountainRankQueryRepository mountainRankQueryRepository) {
        this.climbHistoryQueryRepository = climbHistoryQueryRepository;
        this.climbRecordQueryRepository = climbRecordQueryRepository;
        this.mountainRankQueryRepository = mountainRankQueryRepository;
    }

    @Override
    public FindClimbCheckQueryDTO findClimbCheckByMember(RequestStartClimbMountainQueryDTO request) {

        FindClimbCheckQueryDTO findClimbCheckQueryDTO =
                climbHistoryQueryRepository.selectClimbCheckByMember(request);

        return findClimbCheckQueryDTO;
    }

    @Override
    public List<FindNotCompleteClimbCheckQueryDTO> findClimbNotComplete(RequestStartClimbMountainQueryDTO request) {

        List<FindNotCompleteClimbCheckQueryDTO> notCompleteClimbs =
                climbHistoryQueryRepository.selectAllClimbCheckByMember(request);

        return notCompleteClimbs;
    }

    @Override
    public ResponseRecordMonthlyDTO selectMonthlyRecord(Long id) {
        List<SelectClimbingRecordMonthlyDTO> selectClimbingRecordMonthlyDTO
                = climbRecordQueryRepository.SelectClimbingRecordMonthly(id);

        List<String> months = selectClimbingRecordMonthlyDTO.stream()
                .map(SelectClimbingRecordMonthlyDTO::getMonthly).toList();

        List<Integer> counts = selectClimbingRecordMonthlyDTO.stream()
                .map(SelectClimbingRecordMonthlyDTO::getCount).toList();


        return new ResponseRecordMonthlyDTO(id, months, counts);

    }

    @Override
    public Map<String, SpiritProfileDTO> findMountainRank(HttpServletRequest request) {
        List<MountainRankDTO> mountainRank = mountainRankQueryRepository.SelectMountainRank();
        List<SpiritProfileDTO>  spiritProfiles = mountainRankQueryRepository.selectSpiritProfileMountain();

        changeImagePath(spiritProfiles, request);

        Map<String, SpiritProfileDTO> profileByFrtrlId = spiritProfiles.stream()
                .collect(Collectors.toMap(
                        SpiritProfileDTO::getFrtrlId, // key: "0000000047" 같은 산 ID
                        sp -> sp,                     // value: 그대로 DTO 보관
                        (a, b) -> a                   // 충돌 시 앞의 값 사용 (중복 없다는 가정)
                ));

        mountainRank.stream()
                .forEach(x -> {
                    SpiritProfileDTO profile  =  profileByFrtrlId.get(x.getFrtrlId());
                    if(profile  !=null){
                        profile.getMountainRanks().add(x);
                    }
                });

        return profileByFrtrlId;
    }

    // 프론트에서 이미지 파일 접근할 수 있게 url 주소로 이미지 path 변경
    private void changeImagePath(List<SpiritProfileDTO>  spiritProfiles, HttpServletRequest request){
        spiritProfiles.forEach(x -> {
            String profilePath = x.getFilePath();
            if(profilePath != null){
                String scheme = request.getScheme();         // http / https
                String serverName = request.getServerName(); // localhost
                int port = 8081;         // 8000

                String requestPath = scheme + "://" + serverName +  ":" + port;

                int index = profilePath.indexOf("/img");
                profilePath =  requestPath + profilePath.substring(index,profilePath.length());

                x.setFilePath(profilePath);
            }
        });
    }


}
