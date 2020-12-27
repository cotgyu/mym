package com.mym.sk.service.common;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final RestTemplate restTemplate;

    @Value("${riot_apiKey}")
    private String apiKey;

    // TODO 호출 에러 처리
    public String getJsonDateFromRiotApi(URI url) {

        // riot api는 헤더에만 키만 넣고 있어서 파라미터는 불필요
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("X-Riot-Token", apiKey);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);


        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        return responseEntity.getBody();

    }


    public URI makeRiotApiURI(String url, Map<String, String> pathParams , MultiValueMap<String, String> queryParams){

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParams(queryParams);

        return builder.buildAndExpand(pathParams).encode().toUri();
    }
}
