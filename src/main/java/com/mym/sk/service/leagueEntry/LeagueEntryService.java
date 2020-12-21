package com.mym.sk.service.leagueEntry;

import com.mym.sk.domains.summoner.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeagueEntryService {

    private SummonerRepository summonerRepository;
}
