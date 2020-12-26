package com.mym.yd.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mym.yd.domain.summoner.YdSummoner;
import com.mym.yd.web.dto.SummonerResponseDto;
import com.mym.yd.service.summoner.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@PropertySource("classpath:yd-api-info.properties")
@RequiredArgsConstructor
@RequestMapping(value = "/summoner")
@RestController
public class SummonerController {

    private final SummonerService summonerService;
    @Value("${yd-api-key}")
    private String apiKey;
    @Value("${yd-url}")
    private String riotUrl;
    @Value("${yd-queue}")
    private String queue;
    @Value("${yd-tier}")
    private List<String> tier;
    @Value("${yd-division}")
    private List<String> division;


    @GetMapping("/selectTier")
    public String findByTier() throws JsonProcessingException {

        /**
         * TODO: 2020-12-21
         * 필요한 변수는 매치(솔로랭크 언랭크 등등) , 티어 , 큐 , 페이지
         * 매치와,티어,큐 정보는 저장후에 api호출시에 반복문으로 실행
         * 페이지는 최초 1페이지 호출 후 반환된 건수가 205건이 되지 않으면 미호출, 205건이면 다음페이지로 호출
         */

        /*ArrayList<YdSummoner> leagueEntryDTOArrayList =
                summonerService.getleagueEntryDTOArrayList(
                        summonerService.getSummonerUrl(riotUrl, queue, tier, division, 1, apiKey));*/

        /*return leagueEntryDTOArrayList.size()!=0?"성공":"실패";*/

        return "로그창에 URL찍기 테스트";
    }

    @PostMapping("/selectOneSummoner")
    public SummonerResponseDto selectOneSummoner() {
          return summonerService.findByName("야 뚱");
    }

    @GetMapping("/insertSummoner")
    public void insertSummoner() {
        tier.forEach(tier -> {
            division.forEach(division -> {
                summonerService.saveAll(summonerService.getleagueEntryDTOArrayList(
                        summonerService.getSummonerUrl(riotUrl, queue, tier, division, 1, apiKey)));
            });
        });
    }
}
