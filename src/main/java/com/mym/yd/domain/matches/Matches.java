package com.mym.yd.domain.matches;

import com.mym.yd.domain.summoner.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Matches extends BaseTimeEntity {


    @Id
    private String id;

}
