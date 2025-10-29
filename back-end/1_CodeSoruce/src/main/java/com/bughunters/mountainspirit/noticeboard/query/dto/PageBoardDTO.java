package com.bughunters.mountainspirit.noticeboard.query.dto;

import com.bughunters.mountainspirit.common.PagingButtonInfo;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PageBoardDTO {
    private List<BoardNameDTO> boardList;
    private PagingButtonInfo pagingInfo;
}
