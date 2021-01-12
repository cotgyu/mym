package com.mym.yd.domain.match;

import com.mym.yd.domain.summoner.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Matches extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long gameId;
    private String role;
    private int season;
    private String platformId;
    private int champion;
    private int queue;
    private String lane;
    private long timestamp;

    @Builder
    public Matches(long gameId, String role, int season, String platformId, int champion,
                   int queue, String lane, long timestamp) {
        this.gameId = gameId;
        this.role = role;
        this.season = season;
        this.platformId = platformId;
        this.champion = champion;
        this.queue = queue;
        this.lane = lane;
        this.timestamp = timestamp;
    }
}
