package com.mym.sk.web.dto;


import com.mym.sk.domains.matchList.MatchReference;
import com.mym.sk.domains.summoner.Summoner;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchReferenceSaveDto {

    long gameId;
    String role;
    int season;
    String platformId;
    int champion;
    int queue;
    String lane;
    long timestamp;

    public MatchReference toEntity(){
        return MatchReference.builder()
                .gameId(gameId)
                .role(role)
                .season(season)
                .platformId(platformId)
                .champion(champion)
                .queue(queue)
                .lane(lane)
                .timestamp(timestamp)
                .build();
    }
}
