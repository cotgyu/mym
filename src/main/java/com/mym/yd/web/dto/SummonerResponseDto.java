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
    private MiniSeriesDTO miniSeries;

    public YdSummoner toEntity() {
        return YdSummoner.builder()
                .summonerId(summonerId)
                .leagueId(leagueId)
                .summonerName(summonerName)
                .queueType(queueType)
                .tier(tier)
                .rank(rank)
                .leaguePoints(leaguePoints)
                .wins(wins)
                .losses(losses)
                .hotStreak(hotStreak)
                .veteran(veteran)
                .freshBlood(freshBlood)
                .inactive(inactive)
                .build();
    }

}
