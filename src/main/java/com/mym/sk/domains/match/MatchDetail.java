package com.mym.sk.domains.match;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchDetail {

    @Id @GeneratedValue
    @Column(name = "matchdetail_id")
    private long id;

    private long gameId;

    @OneToMany(mappedBy = "matchDetail")
    private List<ParticipantIdentityDetail> participantIdentities;

    private int queueId;

    private String gameType;

    private long gameDuration;

    @OneToMany(mappedBy = "matchDetail")
    private List<TeamStatsDetail> teams;

    private String platformId;

    private long gameCreation;

    private int seasonId;

    private String gameVersion;

    private int mapId;

    private String gameMode;

    @OneToMany(mappedBy = "matchDetail")
    private List<ParticipantDetail> participants;
}
