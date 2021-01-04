package com.mym.sk.web;

import com.google.gson.Gson;
import com.mym.sk.domains.matchList.MatchReference;
import com.mym.sk.domains.summoner.Summoner;
import com.mym.sk.service.common.CommonService;
import com.mym.sk.service.matchList.MatchListService;
import com.mym.sk.service.summoner.SummonerDetailService;
import com.mym.sk.web.dto.MatchListResponseDto;
import com.mym.sk.web.dto.MatchListSaveDto;
import com.mym.sk.web.dto.SummonerResponseDto;
import com.mym.sk.web.dto.SummonerSaveDto;
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

    private final Gson gson;

    private final ModelMapper modelMapper;

    @Value("${riot_getSummonerInfoURL}")
    private String riot_getSummonerInfoURL;

    @Value("${riot_getSummonerMatchList}")
    private String riot_getSummonerMatchList;


    @GetMapping("/search/{summonerName}")
    public String getSummonerInfo(Model model, @PathVariable String summonerName){

        Optional<SummonerResponseDto> optionalDto = summonerDetailService.getSummonerDetail(summonerName);

        if(optionalDto.isPresent()){
            SummonerResponseDto summonerResponseDto = optionalDto.get();

            List<MatchListResponseDto> matchListResponseDtos = summonerResponseDto.getMatchReferences().stream()
                    .map(MatchReference -> new MatchListResponseDto(MatchReference))
                    .collect(Collectors.toList());

            model.addAttribute("summonerDetail", summonerResponseDto);
            model.addAttribute("matchReferences", matchListResponseDtos);

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

        // TODO 자세한 승패정보 호출 후 매핑 필요
        // match List 조회
        pathParams = new HashMap<>();
        pathParams.put("encryptedAccountId", summoner.getAccountId());

        queryParams = new LinkedMultiValueMap<>();
        queryParams.add("beginIndex", "0");
        queryParams.add("endIndex", "10");

        String matchListData;

        // TODO 중복코드 제거 고려
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


        model.addAttribute("summonerDetail", new SummonerResponseDto(summoner));
        model.addAttribute("matchReferences", matchListResponseDtos);

        return "sk/summoner/summonerDetail";
    }

}
