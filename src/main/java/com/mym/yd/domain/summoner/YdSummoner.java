package com.mym.yd.domain.summoner;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@IdClass(YdSummonerId.class)
@Getter
@NoArgsConstructor
@Entity
public class YdSummoner extends BaseTimeEntity {

    @Id
    private String summonerId;
    @Id
    private String queueType;
    private String leagueId;
    private String summonerName;
    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;
    private boolean hotStreak;
    private boolean veteran;
    private boolean freshBlood;
    private boolean inactive;

    @Builder
    public YdSummoner(String summonerId, String leagueId, String summonerName, String queueType,
                      String tier, String rank, int leaguePoints, int wins, int losses, boolean hotStreak,
                      boolean veteran, boolean freshBlood, boolean inactive) {
        this.summonerId = summonerId;
        this.leagueId = leagueId;
        this.summonerName = summonerName;
        this.queueType = queueType;
        this.tier = tier;
        this.rank = rank;
        this.leaguePoints = leaguePoints;
        this.wins = wins;
        this.losses = losses;
        this.hotStreak = hotStreak;
        this.veteran = veteran;
        this.freshBlood = freshBlood;
        this.inactive = inactive;
    }

}
