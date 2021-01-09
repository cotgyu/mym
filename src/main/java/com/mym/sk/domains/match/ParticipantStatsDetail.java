package com.mym.sk.domains.match;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantStatsDetail {

    @Id
    @GeneratedValue
    @Column(name = "participantstatsdetail_id")
    private long id;

    int kills;
    int deaths;
    int assists;

    @OneToOne(mappedBy = "stats")
    private ParticipantDetail participantDetail;
}
