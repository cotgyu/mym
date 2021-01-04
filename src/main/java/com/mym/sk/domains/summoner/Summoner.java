package com.mym.sk.domains.summoner;

import com.mym.sk.domains.BaseEntity;
import com.mym.sk.domains.matchList.MatchReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Summoner extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "summoner_id")
    Long pkId;

    String accountId;
    int profileIconId;
    long revisionDate;
    String name;

    // Encrypted summoner ID
    String id;

    String puuid;
    long summonerLevel;

    @OneToMany(mappedBy = "summoner")
    private List<MatchReference> matchReferences = new ArrayList<>();
}
