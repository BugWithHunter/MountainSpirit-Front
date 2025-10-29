package com.bughunters.mountainspirit.admin.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClimbStateDTO {
    private List<TotalStampBookmarkDTO> totalStampBookmark;
    private List<ClimbStartDTO> climbStartState;
    private List<ClimbEndDTO> climbEndState;
}
