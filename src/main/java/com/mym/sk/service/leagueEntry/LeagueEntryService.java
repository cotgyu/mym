package com.mym.sk.service.leagueEntry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mym.sk.domains.leagueEntry.LeagueEntry;
import com.mym.sk.domains.leagueEntry.LeagueEntryRepository;
import com.mym.sk.domains.summoner.SummonerRepository;
import com.mym.sk.web.dto.LeagueEntrySaveDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeagueEntryService {

    private final LeagueEntryRepository leagueEntryRepository;

    private final RestTemplate restTemplate;

    // TODO 반영안하는 propertie에 옮길 것
    protected static final String apiKey = "RGAPI-710b75a3-02fd-492c-924b-8bbb96fd06d7";


    public Set getDataSetFromRiotApi(URI url){

        // riot api는 헤더에만 키만 넣고 있어서 파라미터는 불필요
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("X-Riot-Token", apiKey);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);

        ResponseEntity<Set> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Set.class);

        return responseEntity.getBody();

    }

    public List<LeagueEntry> saveLeagueEntriesInfo(ArrayList<LeagueEntrySaveDto> saveDtos){

        List<LeagueEntry> collect = saveDtos.stream()
                .map(LeagueEntrySaveDto::toEntity)
                .collect(Collectors.toList());

        return leagueEntryRepository.saveAll(collect);

    }
}
