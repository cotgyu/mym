package com.mym.sk.web.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mym.sk.domains.leagueEntry.LeagueEntry;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueEntrySaveDto {

    String summonerId;

    String leagueId;

    String summonerName;
    String queueType;
    String tier;
    String rank;
    int leaguePoints;
    int wins;
    int losses;
    boolean hotStreak;
    boolean veteran;
    boolean freshBlood;
    boolean inactive;

    public LeagueEntry toEntity(){
        return LeagueEntry.builder()
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

