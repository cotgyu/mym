package com.mym.sk.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SummonerMatchListResponseDto {

    private long gameId;
    private int champion;
    private String role;

    private String win;

    private int kills;

    private int deaths;

    private int assists;
}
