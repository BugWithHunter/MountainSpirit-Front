package com.bughunters.mountainspirit.crewboard.query.dto;

import com.bughunters.mountainspirit.common.PagingButtonInfo;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PageBoardDTO {
    private List<CrewBoardNameDTO> boardList;
    private PagingButtonInfo pagingInfo;
}
