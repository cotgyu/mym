package com.mym.sk.web.dto;


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
}
