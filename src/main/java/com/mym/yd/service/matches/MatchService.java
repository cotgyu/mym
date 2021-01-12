package com.mym.yd.service.matches;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mym.yd.domain.match.MatchRepository;
import com.mym.yd.domain.match.MatchesRepository;
import com.mym.yd.web.dto.MatchDto;
import com.mym.yd.web.dto.MatchlistDto;
import com.mym.yd.web.vo.UrlVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.http.HttpHeaders;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final MatchesRepository matchesRepository;
    private final RestTemplate restTemplate;
    private HttpHeaders headers;
    private final HttpEntity<?> entity = new HttpEntity<>(headers);
    private final ObjectMapper mapper;
    @Autowired
    private UrlVO urlVO;

    public String getMatchesUrl(String accountId) {
        return urlVO.getMatchlistsUrl() + "/" + accountId + "?api_key=" + urlVO.getApiKey();
    }

    public String getMatchUrl(String matchId) {
        return urlVO.getMatchUrl() + "/" + matchId + "?api_key=" + urlVO.getApiKey();
    }

    public MatchlistDto getMatches(String accountId) {
        return restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(getMatchesUrl(accountId)).build().toString(), HttpMethod.GET, entity, MatchlistDto.class).getBody();
    }

    public void saveAll(String accountId) {
        matchesRepository.saveAll(new ArrayList(getMatches(accountId).getMatches()));
    }

    public void saveMatch(String matchId) {
        MatchDto matchDto = restTemplate.exchange(UriComponentsBuilder.fromHttpUrl(getMatchUrl(matchId)).build().toString(), HttpMethod.GET, entity, MatchDto.class).getBody();
        matchRepository.save(matchDto.toEntity());
    }

}
