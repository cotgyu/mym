package com.mym.yd.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mym.yd.domain.summoner.YdSummoner;
import com.mym.yd.web.dto.SummonerResponseDto;
import com.mym.yd.service.summoner.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
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
    String apiKey;
    @Value("${yd-url}")
    String riotUrl;
    String ranked = "/RANKED_SOLO_5x5";
    String tier = "/IRON";
    String queue = "/I";
    int page = 1;

    @GetMapping("/selectTier")
    public String findByTier() throws JsonProcessingException {

        /**
         * TODO: 2020-12-21
         * 필요한 변수는 매치(솔로랭크 언랭크 등등) , 티어 , 큐 , 페이지
         * 매치와,티어,큐 정보는 저장후에 api호출시에 반복문으로 실행
         * 페이지는 최초 1페이지 호출 후 반환된 건수가 205건이 되지 않으면 미호출, 205건이면 다음페이지로 호출
         */

        ArrayList<YdSummoner> leagueEntryDTOArrayList =
                summonerService.getleagueEntryDTOArrayList(
                        summonerService.getSummonerUrl(riotUrl, ranked, tier, queue, page, apiKey));

        return leagueEntryDTOArrayList.size()!=0?"성공":"실패";
    }

    @GetMapping("/insertSummoner")
    public String insertSummoner() {
        ArrayList<YdSummoner> leagueEntryDTOArrayList =
                summonerService.getleagueEntryDTOArrayList(
                        summonerService.getSummonerUrl(riotUrl, ranked, tier, queue, page, apiKey));
        return summonerService.saveAll(leagueEntryDTOArrayList);
    }
}
