package com.mym.sk.web.dto;

import com.mym.sk.domains.matchList.MatchReference;
import com.mym.sk.domains.summoner.Summoner;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchListResponseDto {

    private long id;

    private long gameId;
    private String role;
    private int season;
    private String platformId;
    private int champion;
    private int queue;
    private String lane;
    private long timestamp;

    private Summoner summoner;

    public MatchListResponseDto(MatchReference matchReference){
        this.id = matchReference.getId();
        this.gameId = matchReference.getGameId();
        this.role = matchReference.getRole();
        this.season = matchReference.getSeason();
        this.platformId = matchReference.getPlatformId();
        this.champion = matchReference.getChampion();
        this.queue = matchReference.getQueue();
        this.lane = matchReference.getLane();
        this.timestamp = matchReference.getTimestamp();
        this.summoner = matchReference.getSummoner();
    }

}
