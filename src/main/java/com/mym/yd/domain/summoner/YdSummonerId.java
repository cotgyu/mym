package com.mym.yd.domain.summoner;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@NoArgsConstructor
public class YdSummonerId implements Serializable {

    @Id
    private String summonerId;
    @Id
    private String queueType;

}
