package com.mym.yd.web.dto;

import com.mym.yd.domain.match.Matches;
import lombok.Getter;

import java.util.List;

@Getter
public class MatchlistDto {
    private int startIndex;
    private int totalGames;
    private int endIndex;
    private List<Matches> matches;
}
