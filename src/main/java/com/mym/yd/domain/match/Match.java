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
public class Match extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long gameId;
    private int queueId;
    private String gameType;
    private long gameDuration;
    private String platformId;
    private long gameCreation;
    private int seasonId;
    private String gameVersion;
    private int mapId;
    private String gameMode;

    @Builder
    public Match(long id, long gameId, int queueId, String gameType, long gameDuration,
                 String platformId, long gameCreation, int seasonId, String gameVersion,
                 int mapId, String gameMode) {
        this.id = id;
        this.gameId = gameId;
        this.queueId = queueId;
        this.gameType = gameType;
        this.gameDuration = gameDuration;
        this.platformId = platformId;
        this.gameCreation = gameCreation;
        this.seasonId = seasonId;
        this.gameVersion = gameVersion;
        this.mapId = mapId;
        this.gameMode = gameMode;
    }
}
