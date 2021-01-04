package com.mym.sk.web.dto;

import com.mym.sk.domains.matchList.MatchReference;
import com.mym.sk.domains.summoner.Summoner;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SummonerResponseDto {

    String accountId;
    int profileIconId;
    long revisionDate;
    String name;

    // Encrypted summoner ID
    String id;

    String puuid;
    long summonerLevel;

    private List<MatchReference> matchReferences = new ArrayList<>();

    public SummonerResponseDto(Summoner entity){
        this.accountId = entity.getAccountId();
        this.profileIconId = entity.getProfileIconId();
        this.revisionDate = entity.getRevisionDate();
        this.name = entity.getName();
        this.id = entity.getId();
        this.puuid = entity.getPuuid();
        this.summonerLevel = entity.getSummonerLevel();
        this.matchReferences = entity.getMatchReferences();
    }

}
