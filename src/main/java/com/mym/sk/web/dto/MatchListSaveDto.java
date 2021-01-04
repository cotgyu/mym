package com.mym.sk.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MatchListSaveDto {
    int startIndex;
    int totalGames;
    int endIndex;

    ArrayList<MatchReferenceSaveDto> matches;

}
