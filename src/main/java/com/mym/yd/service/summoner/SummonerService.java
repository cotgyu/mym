package com.mym.yd.service.summoner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mym.yd.domain.summoner.SummonerAccountInfo;
import com.mym.yd.domain.summoner.SummonerAccountInfoRepository;
import com.mym.yd.domain.summoner.YdSummoner;
import com.mym.yd.domain.summoner.YdSummonerRepository;
import com.mym.yd.web.dto.SummonerDto;
import com.mym.yd.web.dto.SummonerResponseDto;
import com.mym.yd.web.vo.ParameterVO;
import com.mym.yd.web.vo.UrlVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final SummonerAccountInfoRepository summonerAccountInfoRepository;
    private final RestTemplate restTemplate;
    private HttpHeaders headers;
    private final HttpEntity<?> entity = new HttpEntity<>(headers);
    private final ObjectMapper mapper;
    @Autowired
    private UrlVO urlVO;
    @Autowired
    private ParameterVO parameterVO;


    public String getEntriesUrl(String queue, String tier, String divisioon) {
        return urlVO.getEntriesUrl() + queue + tier + divisioon + "?page=" + 1 + "&api_key=" + urlVO.getApiKey();
    }

    public String getOneSummonerUrl(String summonerName) {
        return urlVO.getSummonerUrl() + "/" + summonerName + "?api_key=" + urlVO.getApiKey();
    }

    public String getSummonerInfoUrl(String encryptedSummonerId) {
        return urlVO.getEntriesUrl() + "/by-summoner/" + encryptedSummonerId + "?api_key=" + urlVO.getApiKey();
    }

    public String getSummonerAccountInfoUrl(String encryptedSummonerId) {
        return urlVO.getSummonerAccountInfoUrl() + "/" + encryptedSummonerId + "?api_key=" + urlVO.getApiKey();
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

    public SummonerAccountInfo getSummonerAccountInfo(String url) {
        return restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(url).build().toString(), HttpMethod.GET, entity, SummonerAccountInfo.class).getBody();
    }

    @Transactional
    public String saveAll() {
        parameterVO.getQueue().forEach(queue -> {
            parameterVO.getTier().forEach(tier -> {
                parameterVO.getDivision().forEach(division -> {
                    // 맵으로 설정
                    List list = new ArrayList(getleagueEntryDTOArrayList(getEntriesUrl(queue, tier, division)));
                    ydSummonerRepository.saveAll(list);
                });
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
        ydSummonerRepository.saveAll(getleagueEntryDTOArrayList(getSummonerInfoUrl(getSummonerDto(getOneSummonerUrl(summonerName)).getId())));
        return new YdSummoner();
    }

    public void getAndSaveAccountInfo() {
        List<SummonerAccountInfo> list = new ArrayList<SummonerAccountInfo>();
        ydSummonerRepository.findAll().forEach(summonerId ->{
            try {
                Thread.sleep(1200);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(getSummonerAccountInfo(getSummonerAccountInfoUrl(summonerId.getSummonerId())));
        });
        summonerAccountInfoRepository.saveAll(list);
    }


}
