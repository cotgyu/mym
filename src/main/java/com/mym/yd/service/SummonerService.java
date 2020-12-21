package com.mym.yd.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mym.yd.dto.LeagueEntryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SummonerService {

    public String getSummonerUrl(String riotUrl, String ranked, String tier, String queue, int page, String apiKey) {

        return riotUrl + ranked + tier + queue + "?page=" + page + "&api_key=" + apiKey;

    }

    public ArrayList<LeagueEntryDTO> getleagueEntryDTOArrayList(String summonerUrl) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ObjectMapper mapper = new ObjectMapper();

        return mapper.convertValue(
                new ArrayList(
                        restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(summonerUrl).build().toString(), HttpMethod.GET, entity, Set.class).getBody()
                ), new TypeReference<ArrayList<LeagueEntryDTO>>() {});
    }


}
