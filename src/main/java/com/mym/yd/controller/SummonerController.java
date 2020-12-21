package com.mym.yd.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mym.base.service.BoardService;
import com.mym.yd.dto.LeagueEntryDTO;
import com.mym.yd.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.http.HttpClient;
import java.util.*;

@RequestMapping(value = "/summoner")
@RequiredArgsConstructor
@RestController
public class SummonerController {

    private final SummonerService summonerService;

    @GetMapping("/selectTier")
    public String findByTier () throws JsonProcessingException {

        /**
         * TODO: 2020-12-21
         * 필요한 변수는 매치(솔로랭크 언랭크 등등) , 티어 , 큐 , 페이지
         * 매치와,티어,큐 정보는 저장후에 api호출시에 반복문으로 실행
         * 페이지는 최초 1페이지 호출 후 반환된 건수가 205건이 되지 않으면 미호출, 205건이면 다음페이지로 호출
         */

        String apiKey = "RGAPI-7e07c0d7-bf45-4e14-959a-ada30ed53380";
        String riotUrl = "https://kr.api.riotgames.com/lol/league/v4/entries";
        String ranked = "/RANKED_SOLO_5x5";
        String tier = "/IRON";
        String queue = "/I";
        int page = 1;

        ArrayList<LeagueEntryDTO> leagueEntryDTOArrayList = summonerService.getleagueEntryDTOArrayList(summonerService.getSummonerUrl(riotUrl, ranked, tier, queue, page, apiKey));

        leagueEntryDTOArrayList.forEach(s -> System.out.print(s.getSummonerName() + ","));
        
        return "요청데이터 확인";
    }

}
