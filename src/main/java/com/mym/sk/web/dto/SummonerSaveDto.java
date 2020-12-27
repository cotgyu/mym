package com.mym.sk.web.dto;

import com.mym.sk.domains.summoner.Summoner;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SummonerSaveDto {

    String accountId;
    int profileIconId;
    long revisionDate;
    String name;

    // Encrypted summoner ID
    String id;

    String puuid;
    long summonerLevel;

    public Summoner toEntity(){
        return Summoner.builder()
                .accountId(accountId)
                .profileIconId(profileIconId)
                .revisionDate(revisionDate)
                .name(name)
                .id(id)
                .puuid(puuid)
                .summonerLevel(summonerLevel).build();
    }

}
