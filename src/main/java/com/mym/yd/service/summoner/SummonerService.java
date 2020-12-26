package com.mym.yd.service.summoner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mym.yd.domain.summoner.YdSummoner;
import com.mym.yd.domain.summoner.YdSummonerRepository;
import com.mym.yd.web.dto.SummonerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SummonerService {

    private final YdSummonerRepository ydSummonerRepository;

    public String getSummonerUrl(String riotUrl, String queue, String tier, String division, int page, String apiKey) {

        return riotUrl + queue + tier + division + "?page=" + page + "&api_key=" + apiKey;

    }

    public ArrayList<YdSummoner> getleagueEntryDTOArrayList(String summonerUrl) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ObjectMapper mapper = new ObjectMapper();

        /**
        * @author yd @since 2020-12-24 오후 4:46 
        * ObjectMapper 클래스 설정에 반환값에 없는 값은 처리하지 않도록 FAIL_ON_UNKNOWN_PROPERTIES , false로 설정 후 진행
        */
        return mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false).convertValue(
                new ArrayList(
                        restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(summonerUrl).build().toString(), HttpMethod.GET, entity, Set.class).getBody()
                ), new TypeReference<ArrayList<YdSummoner>>() {});
    }

    @Transactional
    public String saveAll(ArrayList<YdSummoner> leagueEntryDTOArrayList) {
        List list = new ArrayList(leagueEntryDTOArrayList);
        return "소환사 정보" + ydSummonerRepository.saveAll(list).size() + "건 등록 완료";
    }

    public SummonerResponseDto findByName(String summonerName) {
        YdSummoner entity = ydSummonerRepository.findByName(summonerName).orElseThrow();
        return new SummonerResponseDto(entity);
    }
}
