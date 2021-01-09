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
public class ParticipantIdentityDetail {

    @Id
    @GeneratedValue
    @Column(name = "participantidentitydetail_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "gameId")
    private MatchDetail matchDetail;

    private int participantId;

    @OneToOne(mappedBy = "participantIdentityDetail")
    private PlayerDetail player;


    public void addMatchDetail(MatchDetail matchDetail){
        this.matchDetail = matchDetail;
    }


}
