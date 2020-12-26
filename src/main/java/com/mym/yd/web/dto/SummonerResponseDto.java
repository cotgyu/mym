package com.mym.yd.web.dto;

import com.mym.sk.domains.summoner.Summoner;
import com.mym.yd.domain.summoner.YdSummoner;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class SummonerResponseDto {
    private String leagueId;
    private String summonerId;
    private String summonerName;
    private String queueType;
    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;
    private boolean hotStreak;
    private boolean veteran;
    private boolean freshBlood;
    private boolean inactive;

    public SummonerResponseDto(YdSummoner entity) {
        this.leagueId = entity.getLeagueId();
        this.summonerId = entity.getSummonerId();
        this.summonerName = entity.getSummonerName();
        this.queueType = entity.getQueueType();
        this.tier = entity.getTier();
        this.rank = entity.getRank();
        this.leaguePoints = entity.getLeaguePoints();
        this.wins = entity.getWins();
        this.losses = entity.getLosses();
        this.hotStreak = entity.isHotStreak();
        this.veteran = entity.isVeteran();
        this.freshBlood = entity.isFreshBlood();
        this.inactive = entity.isInactive();
    }
}
