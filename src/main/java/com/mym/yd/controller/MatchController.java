package com.mym.yd.controller;

import com.mym.yd.service.matches.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/match")
@RestController
public class MatchController {

    private final MatchService matchService;

    @GetMapping("/saveMatch")
    public void saveMatch() {
        matchService.saveMatch("4910673072");
    }


}
