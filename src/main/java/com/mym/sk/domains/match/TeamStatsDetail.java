package com.mym.sk.domains.match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamStatsDetail {

    @Id
    @GeneratedValue
    @Column(name = "teamstatsdetail_id")
    private long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "gameId")
    private MatchDetail matchDetail;

    private int teamId;
    private String win;

    public void addMatchDetail(MatchDetail matchDetail){
        this.matchDetail = matchDetail;
    }

}
