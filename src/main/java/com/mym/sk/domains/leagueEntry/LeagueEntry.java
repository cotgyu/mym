package com.mym.sk.domains.leagueEntry;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeagueEntry {

    // Player's encrypted summonerId.
    @Id
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

    // TODO 연관관계 매핑
}
