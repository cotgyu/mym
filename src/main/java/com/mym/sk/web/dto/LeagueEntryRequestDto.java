package com.mym.sk.web.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mym.sk.domains.leagueEntry.LeagueEntry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeagueEntryRequestDto {

    String queue;
    String tier;
    String division;
    int page;
}

