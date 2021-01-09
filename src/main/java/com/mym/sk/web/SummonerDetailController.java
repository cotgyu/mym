package com.mym.sk.web;

import com.google.gson.Gson;
import com.mym.sk.domains.match.MatchDetail;
import com.mym.sk.domains.match.ParticipantDetail;
import com.mym.sk.domains.match.ParticipantIdentityDetail;
import com.mym.sk.domains.match.TeamStatsDetail;
import com.mym.sk.domains.matchList.MatchReference;
import com.mym.sk.domains.summoner.Summoner;
import com.mym.sk.service.common.CommonService;
import com.mym.sk.service.matchDetail.MatchDetailService;
import com.mym.sk.service.matchList.MatchListService;
import com.mym.sk.service.summoner.SummonerDetailService;
import com.mym.sk.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@PropertySource("classpath:riotApiRequestURL.properties")
@RequestMapping("/summoner")
public class SummonerDetailController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SummonerDetailService summonerDetailService;

    private final CommonService commonService;

    private final MatchListService matchListService;

    private final MatchDetailService matchDetailService;

    private final Gson gson;

    private final ModelMapper modelMapper;

    @Value("${riot_getSummonerInfoURL}")
    private String riot_getSummonerInfoURL;

    @Value("${riot_getSummonerMatchList}")
    private String riot_getSummonerMatchList;

    @Value("${riot_getMatchDetail}")
    private String riot_getMatchDetail;


    @GetMapping("/search/{summonerName}")
    public String getSummonerInfo(Model model, @PathVariable String summonerName){

        Optional<SummonerResponseDto> optionalDto = summonerDetailService.getSummonerDetail(summonerName);

        if(optionalDto.isPresent()){
            SummonerResponseDto summonerResponseDto = optionalDto.get();

            List<MatchListResponseDto> matchListResponseDtos = summonerResponseDto.getMatchReferences().stream()
                    .map(MatchReference -> new MatchListResponseDto(MatchReference))
                    .collect(Collectors.toList());


            List<MatchReference> matchReferences = summonerResponseDto.getMatchReferences();

            List<SummonerMatchListResponseDto> matchDetailList = matchDetailService.getMatchDetailList(matchReferences, summonerResponseDto);


            model.addAttribute("summonerDetail", summonerResponseDto);
            model.addAttribute("matchDetailList", matchDetailList);

            return "sk/summoner/summonerDetail";
        }

        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("summonerName", summonerName);

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

        String resultData;

        try {
            resultData = commonService.getJsonDateFromRiotApi(riot_getSummonerInfoURL, pathParams, queryParams);
        } catch (HttpClientErrorException e){

            logger.error("riot api 호출 에러: "+ e.getMessage());

            if(e.getRawStatusCode() == 404){
                model.addAttribute("exception", "사용자를 찾을 수 없습니다.");
            } else {
                model.addAttribute("exception", "API 호출에 실패하였습니다.");
            }

            return "exception";
        }

        Summoner summoner = summonerDetailService.saveSummonerDetail( gson.fromJson(resultData, new TypeToken<SummonerSaveDto>(){}.getType()) );
        model.addAttribute("summonerDetail", new SummonerResponseDto(summoner));

        // match List 조회
        pathParams = new HashMap<>();
        pathParams.put("encryptedAccountId", summoner.getAccountId());

        queryParams = new LinkedMultiValueMap<>();
        queryParams.add("beginIndex", "0");
        queryParams.add("endIndex", "10");

        String matchListData;

        // TODO 중복코드 제거 고려
        // TODO 다중 호출 에러 처리
        try {
            matchListData = commonService.getJsonDateFromRiotApi(riot_getSummonerMatchList, pathParams, queryParams);
        } catch (HttpClientErrorException e){

            logger.error("riot api 호출 에러: "+ e.getMessage());

            if(e.getRawStatusCode() == 404){
                model.addAttribute("exception", "매치정보를 찾을 수 없습니다.");
            } else {
                model.addAttribute("exception", "API 호출에 실패하였습니다.");
            }

            return "exception";
        }

        MatchListSaveDto dto = gson.fromJson(matchListData, new TypeToken<MatchListSaveDto>(){}.getType());


        List<MatchReference> matchReferences = matchListService.saveMatchReferenceList(summoner, dto);

        List<MatchListResponseDto> matchListResponseDtos = matchReferences.stream()
                .map(MatchReference -> new MatchListResponseDto(MatchReference))
                .collect(Collectors.toList());

        // TODO 중복 코드 제거
        // 매치 정보 가져오기
        List<MatchDetail> matchDetailList = new ArrayList<>();

        List<SummonerMatchListResponseDto> matchListResponseDtoList = new ArrayList<>();

        for (MatchListResponseDto matchListResponseDto : matchListResponseDtos) {

            pathParams = new HashMap<>();
            pathParams.put("matchId", Long.toString(matchListResponseDto.getGameId()));

            queryParams = new LinkedMultiValueMap<>();

            String matchDetailData = commonService.getJsonDateFromRiotApi(riot_getMatchDetail, pathParams, queryParams);

            MatchDetail matchDetail = gson.fromJson(matchDetailData, new TypeToken<MatchDetail>(){}.getType());

            matchDetailList.add(matchDetail);


            List<ParticipantIdentityDetail> participantIdentities = matchDetail.getParticipantIdentities();

            int participantId = 0;

            for (ParticipantIdentityDetail participantIdentity : participantIdentities) {
                if(participantIdentity.getPlayer().getAccountId().equals(summoner.getAccountId())){

                    participantId = participantIdentity.getParticipantId();

                    break;
                }
            }

            int kill = -1;
            int deaths = -1;
            int assists = -1;

            List<ParticipantDetail> participants = matchDetail.getParticipants();

            for (ParticipantDetail participant : participants) {

                if(participant.getParticipantId() == participantId){
                    kill = participant.getStats().getKills();
                    deaths = participant.getStats().getDeaths();
                    assists = participant.getStats().getAssists();

                    break;
                }

            }

            int teamId = 100;
            if(participantId > 5){
                teamId = 200;
            }

            String win = "";

            List<TeamStatsDetail> teams = matchDetail.getTeams();
            for (TeamStatsDetail team : teams) {

                if(team.getTeamId() == teamId){
                    win = team.getWin();

                    break;
                }

            }

            matchListResponseDtoList.add(new SummonerMatchListResponseDto(
                    matchListResponseDto.getGameId(), matchListResponseDto.getChampion(), matchListResponseDto.getRole(), win, kill, deaths, assists));
        }

        matchDetailService.saveMatchDetail(matchDetailList);


        model.addAttribute("matchDetailList", matchListResponseDtoList);



        return "sk/summoner/summonerDetail";
    }

}
