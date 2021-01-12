package com.mym.yd.domain.match;

import com.mym.yd.domain.summoner.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Participant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long gameId;
    private int participantId;
    private int profileIcon;
    private String accountId;
    private String matchHistoryUri;
    private String currentAccountId;
    private String currentPlatformId;
    private String summonerName;
    private String summonerId;
    private String platformId;

    @Builder
    public Participant(long gameId, int participantId, int profileIcon, String accountId,
                       String matchHistoryUri, String currentAccountId, String currentPlatformId,
                       String summonerName, String summonerId, String platformId) {
        this.gameId = gameId;
        this.participantId = participantId;
        this.profileIcon = profileIcon;
        this.accountId = accountId;
        this.matchHistoryUri = matchHistoryUri;
        this.currentAccountId = currentAccountId;
        this.currentPlatformId = currentPlatformId;
        this.summonerName = summonerName;
        this.summonerId = summonerId;
        this.platformId = platformId;
    }
}
