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
public class PlayerDetail {

    @Id
    @GeneratedValue
    @Column(name = "playerdetail_id")
    private long id;

    private int profileIcon;
    private String accountId;
    private String matchHistoryUri;
    private String currentAccountId;
    private String currentPlatformId;
    private String summonerName;
    private String summonerId;
    private String platformId;

    @OneToOne
    @JoinColumn(name = "participantidentitydetail_id")
    private ParticipantIdentityDetail participantIdentityDetail;


    public void addParticipantIdentityDetail(ParticipantIdentityDetail participantIdentityDetail){
        this.participantIdentityDetail = participantIdentityDetail;
    }
}
