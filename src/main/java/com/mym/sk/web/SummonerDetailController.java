package com.mym.sk.web;

import com.google.gson.Gson;
import com.mym.sk.domains.summoner.Summoner;
import com.mym.sk.service.common.CommonService;
import com.mym.sk.service.summoner.SummonerDetailService;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@PropertySource("classpath:riotApiRequestURL.properties")
@RequestMapping("/summoner")
public class SummonerDetailController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SummonerDetailService summonerDetailService;

    private final CommonService commonService;

    private final Gson gson;

    private final ModelMapper modelMapper;

    @Value("${riot_getSummonerInfoURL}")
    private String riot_getSummonerInfoURL;


    // TODO 매치정보 call - 사용자랑 연관관계 매핑
    @GetMapping("/search/{summonerName}")
    public String getSummonerInfo(Model model, @PathVariable String summonerName){

        Optional<SummonerResponseDto> optionalDto = summonerDetailService.getSummonerDetail(summonerName);

        if(optionalDto.isPresent()){
            model.addAttribute("summonerDetail", optionalDto.get());

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

        return "sk/summoner/summonerDetail";
    }

}
