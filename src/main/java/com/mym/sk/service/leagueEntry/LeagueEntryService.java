package com.mym.sk.service.leagueEntry;

import com.mym.sk.domains.leagueEntry.LeagueEntry;
import com.mym.sk.domains.leagueEntry.LeagueEntryRepository;
import com.mym.sk.web.dto.LeagueEntrySaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:riotApiRequestURL.properties")
public class LeagueEntryService {

    private final LeagueEntryRepository leagueEntryRepository;

    private final RestTemplate restTemplate;

    @Value("${riot_apiKey}")
    private String apiKey;


    public String getJsonDateFromRiotApi(URI url){

        // riot api는 헤더에만 키만 넣고 있어서 파라미터는 불필요
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("X-Riot-Token", apiKey);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);


        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        return responseEntity.getBody();

    }

    public List<LeagueEntry> saveLeagueEntriesInfo(ArrayList<LeagueEntrySaveDto> saveDtos){

        List<LeagueEntry> collect = saveDtos.stream()
                .map(LeagueEntrySaveDto::toEntity)
                .collect(Collectors.toList());

        return leagueEntryRepository.saveAll(collect);

    }

    public URI makeRiotApiURI(String url, Map<String, String> pathParams , MultiValueMap<String, String> queryParams){

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParams(queryParams);

        return builder.buildAndExpand(pathParams).encode().toUri();
    }
}
