package com.mym.sk.domains.match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDetail {

    @Id
    @GeneratedValue
    @Column(name = "participantdetail_id")
    private long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gameId")
    private MatchDetail matchDetail;

    private int participantId;
    private int championId;

    @OneToOne
    @JoinColumn(name = "participantstatsdetail_id")
    private ParticipantStatsDetail stats;

    private int teamId;

    public void addMatchDetail(MatchDetail matchDetail){
        this.matchDetail = matchDetail;
    }


}
