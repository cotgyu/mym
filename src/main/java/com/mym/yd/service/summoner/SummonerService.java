package com.mym.yd.service.summoner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mym.yd.domain.summoner.YdSummoner;
import com.mym.yd.domain.summoner.YdSummonerRepository;
import com.mym.yd.web.dto.SummonerDto;
import com.mym.yd.web.dto.SummonerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@PropertySource("classpath:yd-api-info.properties")
@Service
@RequiredArgsConstructor
public class SummonerService {

    private final YdSummonerRepository ydSummonerRepository;
    private final RestTemplate restTemplate;
    private HttpHeaders headers;
    private final HttpEntity<?> entity = new HttpEntity<>(headers);
    private final ObjectMapper mapper;
    @Value("${yd-api-key}")
    private String apiKey;
    @Value("${yd-entries-url}")
    private String entriesUrl;
    @Value("${yd-summoner-url}")
    private String summonerUrl;
    @Value("${yd-queue}")
    private String queue;
    @Value("${yd-tier}")
    private List<String> tier;
    @Value("${yd-division}")
    private List<String> division;

    public String getEntriesUrl(String tier, String division) {
        return entriesUrl + queue + tier + division + "?page=" + 1 + "&api_key=" + apiKey;
    }

    public String getOneSummonerUrl(String summonerName) {
        return summonerUrl + "/" + summonerName + "?api_key=" + apiKey;
    }

    public String getOneSummonerInfoUrl(String encryptedSummonerId) {
        return summonerUrl + "/by-summoner/" + encryptedSummonerId + "&api_key=" + apiKey;
    }

    public ArrayList<YdSummoner> getleagueEntryDTOArrayList(String summonerUrl) {

        /**
        * @author yd @since 2020-12-24 오후 4:46 
        * ObjectMapper 클래스 설정에 반환값에 없는 값은 처리하지 않도록 FAIL_ON_UNKNOWN_PROPERTIES , false로 설정 후 진행
        */
        return mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false).convertValue(
                new ArrayList(
                        restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(summonerUrl).build().toString(), HttpMethod.GET, entity, Set.class).getBody()
                ), new TypeReference<ArrayList<YdSummoner>>() {});
    }

    public SummonerDto getSummonerDto(String url) {
        return restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(url).build().toString(), HttpMethod.GET, entity, SummonerDto.class).getBody();
    }

    public YdSummoner getYdSummoner(String url) {
        return restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(url).build().toString(), HttpMethod.GET, entity, YdSummoner.class).getBody();
    }

    @Transactional
    public String saveAll() {
        tier.forEach(tier -> {
            division.forEach(division -> {
                List list = new ArrayList(getleagueEntryDTOArrayList(getEntriesUrl(tier, division)));
                ydSummonerRepository.saveAll(list);
            });
        });
        return "입력완료";
    }

    public SummonerResponseDto findByName(String summonerName) {
        YdSummoner entity = ydSummonerRepository.findByName(summonerName).orElseGet(() ->
                insertAndSelectSummoner(summonerName)
        );
        return new SummonerResponseDto(entity);
    }

    public YdSummoner insertAndSelectSummoner(String summonerName) {
        return getYdSummoner(getOneSummonerInfoUrl(getSummonerDto(getOneSummonerUrl(summonerName)).getId()));
    }

}
