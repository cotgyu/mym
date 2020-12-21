package com.mym.sk.web;

import com.mym.sk.service.leagueEntry.LeagueEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/league")
public class LeagueEntryCallApiController {

    private LeagueEntryService leagueEntryService;

    @RequestMapping("/entries/")
    public ResponseEntity entryCall(){
        Map<String, Object> resultMap = new HashMap<>();


        // TODO entries api 결과 받아서 담기

        // TODO 결과 사용자 결과 받아서 담기

        // TODO 데이터 DB 저장


        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
