package com.mym.sk.web;

import com.google.gson.Gson;
import com.mym.sk.domains.summoner.Summoner;
import com.mym.sk.service.common.CommonService;
import com.mym.sk.service.summoner.SummonerDetailService;
import com.mym.sk.web.dto.SummonerRequestDto;
import com.mym.sk.web.dto.SummonerSaveDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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

        // TODO optional 사용 고려
        SummonerRequestDto summonerRequestDto = summonerDetailService.getSummonerInfoFromDB(summonerName);

        if(summonerRequestDto.getAccountId() == null || summonerRequestDto.getAccountId().equals("")){

            Map<String, String> pathParams = new HashMap<>();
            pathParams.put("summonerName",summonerName);

            MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();

            URI uri = commonService.makeRiotApiURI(riot_getSummonerInfoURL, pathParams, queryParams);

            String resultData = commonService.getJsonDateFromRiotApi(uri);

            Summoner summoner = summonerDetailService.saveSummonerDetail( gson.fromJson(resultData, new TypeToken<SummonerSaveDto>(){}.getType()) );

            modelMapper.map(summoner, summonerRequestDto);
        }

        model.addAttribute("summonerDetail", summonerRequestDto);

        return "sk/summoner/summonerDetail";
    }

}
