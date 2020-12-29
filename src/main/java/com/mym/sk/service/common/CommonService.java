package com.mym.sk.service.common;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${riot_apiKey}")
    private String apiKey;

    /**
     * riot api 호출
     * @param url
     * @param pathParams
     * @param queryParams
     * @return
     */
    public String getJsonDateFromRiotApi(String url, Map<String, String> pathParams , MultiValueMap<String, String> queryParams) {

        URI callURI = makeRiotApiURI(url, pathParams, queryParams);

        // riot api는 헤더에만 키만 넣고 있어서 파라미터는 불필요
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("X-Riot-Token", apiKey);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(callURI, HttpMethod.GET, requestEntity, String.class);
        String result = responseEntity.getBody();

        return result;
    }


    /**
     * api 호출할 URI 생성
     * @param url
     * @param pathParams
     * @param queryParams
     * @return
     */
    private URI makeRiotApiURI(String url, Map<String, String> pathParams , MultiValueMap<String, String> queryParams){

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParams(queryParams);

        return builder.buildAndExpand(pathParams).encode().toUri();
    }
}
