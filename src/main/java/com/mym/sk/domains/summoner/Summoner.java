package com.mym.sk.domains.summoner;

import com.mym.sk.domains.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Summoner extends BaseEntity {

    @Id @GeneratedValue
    Long pkId;

    String accountId;
    int profileIconId;
    long revisionDate;
    String name;

    // Encrypted summoner ID
    String id;

    String puuid;
    long summonerLevel;

    // TODO 연관관계 매핑
}
