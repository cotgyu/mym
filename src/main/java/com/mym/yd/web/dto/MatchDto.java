package com.mym.yd.web.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MatchDto {
    private long gameId;
    private List<ParticipantIdentityDto> participantList;
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
}
