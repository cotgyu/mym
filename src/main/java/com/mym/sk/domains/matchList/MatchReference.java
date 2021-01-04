package com.mym.sk.domains.matchList;

import com.mym.sk.domains.summoner.Summoner;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchReference {

    @Id @GeneratedValue
    @Column(name="matchreference_id")
    private long id;

    private long gameId;
    private String role;
    private int season;
    private String platformId;
    private int champion;
    private int queue;
    private String lane;
    private long timestamp;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "summoner_id")
    private Summoner summoner;

}
