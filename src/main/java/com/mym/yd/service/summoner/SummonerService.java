package com.mym.yd.service.summoner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mym.yd.domain.summoner.YdSummonerRepository;
import com.mym.yd.web.dto.SummonerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SummonerService {

    private final YdSummonerRepository ydSummonerRepository;

    public String getSummonerUrl(String riotUrl, String ranked, String tier, String queue, int page, String apiKey) {

        return riotUrl + ranked + tier + queue + "?page=" + page + "&api_key=" + apiKey;

    }

    public ArrayList<SummonerResponseDto> getleagueEntryDTOArrayList(String summonerUrl) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ObjectMapper mapper = new ObjectMapper();

        return mapper.convertValue(
                new ArrayList(
                        restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(summonerUrl).build().toString(), HttpMethod.GET, entity, Set.class).getBody()
                ), new TypeReference<ArrayList<SummonerResponseDto>>() {});
    }

    public void saveAll(ArrayList<SummonerResponseDto> leagueEntryDTOArrayList) {
        //ydSummonerRepository.saveAll(leagueEntryDTOArrayList);
    }

}
