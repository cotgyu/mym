package com.mym.sk.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mym.sk.domains.leagueEntry.LeagueEntry;
import com.mym.sk.service.leagueEntry.LeagueEntryService;
import com.mym.sk.web.dto.LeagueEntrySaveDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.*;

@Controller
@RequiredArgsConstructor
@PropertySource("classpath:riotApiRequestURL.properties")
@RequestMapping("/league")
public class LeagueEntryCallApiController {

    private final LeagueEntryService leagueEntryService;

    private final Gson gson;

    @Value("${riot_leagueEntriesURL}")
    private String riot_leagueEntriesURL;

    @RequestMapping("/entries")
    public ResponseEntity entriesApiCall(){
        Map<String, Object> resultMap = new HashMap<>();

        // TODO 조회 파라미터 입력받기 (pathparam or requestdto)
        String division = "I";
        String tier = "DIAMOND";
        String queue = "RANKED_SOLO_5x5";

        // path 파라미터
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("queue", queue);
        pathParams.put("tier", tier);
        pathParams.put("division", division);

        // query 파라미터
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "2");

        // uri 생성 공통 메서드
        URI uri = leagueEntryService.makeRiotApiURI(riot_leagueEntriesURL, pathParams, queryParams);

        // api 호출 공통 메서드
        String resultData = leagueEntryService.getJsonDateFromRiotApi(uri);

        ArrayList<LeagueEntrySaveDto> leagueEntrySaveDtos = gson.fromJson(resultData, new TypeToken<ArrayList<LeagueEntrySaveDto>>(){}.getType());


        // TODO 사용자 정보도 같이 조회해서 저장 필요
        List<LeagueEntry> leagueEntries = leagueEntryService.saveLeagueEntriesInfo(leagueEntrySaveDtos);

        resultMap.put("result", leagueEntries);

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

}
