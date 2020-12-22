package com.mym.sk.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mym.sk.domains.leagueEntry.LeagueEntry;
import com.mym.sk.service.leagueEntry.LeagueEntryService;
import com.mym.sk.web.dto.LeagueEntrySaveDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.TypeToken;
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
@RequestMapping("/league")
public class LeagueEntryCallApiController {

    private final LeagueEntryService leagueEntryService;

    private final Gson gson;


    // TODO 조회 파라미터 입력받기 (pathparam or requestdto)
    @RequestMapping("/entries")
    public ResponseEntity entriesApiCall(){
        Map<String, Object> resultMap = new HashMap<>();

        // TODO url 생성 공통유틸? 만들기
        String url ="https://kr.api.riotgames.com/lol/league/v4/entries";

        String division = "I";
        String tier = "DIAMOND";
        String queue = "RANKED_SOLO_5x5";
        String slash = "/";

        url = url + slash + queue + slash + tier + slash + division ;

        // queryparam 설정
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "2");

        URI uri = UriComponentsBuilder.fromUriString(url)
                .queryParams(queryParams)
                .build().encode()
                .toUri();


        String resultData = leagueEntryService.getJsonDateFromRiotApi(uri);

        ArrayList<LeagueEntrySaveDto> leagueEntrySaveDtos = gson.fromJson(resultData, new TypeToken<ArrayList<LeagueEntrySaveDto>>(){}.getType());


        // TODO 사용자 정보도 같이 조회해서 저장 필요
        List<LeagueEntry> leagueEntries = leagueEntryService.saveLeagueEntriesInfo(leagueEntrySaveDtos);

        resultMap.put("result", leagueEntries);

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

}
