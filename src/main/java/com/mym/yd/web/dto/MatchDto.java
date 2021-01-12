package com.mym.yd.web.dto;

import com.mym.yd.domain.match.Match;
import lombok.Getter;

import java.util.List;

@Getter
public class MatchDto {
    private long gameId;
    private List<ParticipantIdentityDto> participantIdentities;
    private int queueId;
    private String gameType;
    private long gameDuration;
    private List<TeamStatsDto> teams;
    private String platformId;
    private long gameCreation;
    private int seasonId;
    private String gameVersion;
    private int mapId;
    private String gameMode;
    private List<ParticipantDto> participants;

    public Match toEntity() {
        return Match.builder()
                .gameId(gameId)
                .queueId(queueId)
                .gameType(gameType)
                .gameDuration(gameDuration)
                .platformId(platformId)
                .gameCreation(gameCreation)
                .seasonId(seasonId)
                .gameVersion(gameVersion)
                .mapId(mapId)
                .gameMode(gameMode)
                .build();
    }

}
