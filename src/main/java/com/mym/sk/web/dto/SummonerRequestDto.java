package com.mym.sk.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SummonerRequestDto {

    String accountId;
    int profileIconId;
    long revisionDate;
    String name;

    // Encrypted summoner ID
    String id;

    String puuid;
    long summonerLevel;

}
