package com.mym.sk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mym.sk.web.dto.LeagueEntrySaveDto;
import jdk.jfr.Description;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.map;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RiotApiTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ModelMapper modelMapper;


    // 개발용이라 하루짜리
    @Value("${riot_apiKey}")
    private String apiKey;

    @Test
    @DisplayName("챔피언 로테이션 api 호출이 되는 지 확인한다.")
    public void champion_rotation_api_call_test() throws Exception{
        // https://kr.api.riotgames.com/lol/platform/v3/champion-rotations?api_key=RGAPI-13c7e003-66d4-4700-b3d4-3424fdb6a607

        // given
        String url = "https://kr.api.riotgames.com/lol/platform/v3/champion-rotations";


        RestTemplate restTemplate = new RestTemplate();

        // when
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("X-Riot-Token", apiKey);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);


        // then
        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Map.class);
        Map<String, Object> responseMap = responseEntity.getBody();

        assertThat(responseMap.size()).isNotNegative();

        System.out.println(responseMap);
    }

    @Test
    @DisplayName("티어별 데이터를 받아오는 api 호출-파싱 테스트")
    public void league_entries_api_call_test() throws Exception {

        // https://kr.api.riotgames.com/lol/league/v4/entries/RANKED_SOLO_5x5/DIAMOND/I?page=1

        // given
        String url ="https://kr.api.riotgames.com/lol/league/v4/entries";

        String division = "I";
        String tier = "DIAMOND";
        String queue = "RANKED_SOLO_5x5";
        String slash = "/";
        String pageParam = "?page=1";


        url = url + slash + queue + slash + tier + slash + division + pageParam;

        RestTemplate restTemplate = new RestTemplate();

        // when
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("X-Riot-Token", apiKey);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);

        ResponseEntity<Set> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Set.class);


        // then
        Set<LinkedHashMap> responseSet = responseEntity.getBody();
        System.out.println(responseSet.size());

        assertThat(responseSet.size()).isNotNegative();

        ObjectMapper mapper = new ObjectMapper();

        ArrayList<LeagueEntrySaveDto> dto = mapper.convertValue(
                new ArrayList(
                        responseSet
                ), new TypeReference<ArrayList<LeagueEntrySaveDto>>() {});


        Object[] objects = responseSet.toArray();
        JSONArray jsonArray = new JSONArray(objects);
        //System.out.println(jsonArray);
        JSONObject getFirst = (JSONObject) jsonArray.get(0);

        System.out.println(getFirst);
        assertThat(getFirst.get("tier")).isEqualTo(tier);

    }

    @Test
    @DisplayName("티어별 데이터를 받아오는 api 호출-파싱 테스트2 gson 사용")
    public void league_entries_api_call_test2() throws Exception {

        // https://kr.api.riotgames.com/lol/league/v4/entries/RANKED_SOLO_5x5/DIAMOND/I?page=1

        // given
        String url ="https://kr.api.riotgames.com/lol/league/v4/entries/{queue}/{tier}/{division}";

        String division = "I";
        String tier = "DIAMOND";
        String queue = "RANKED_SOLO_5x5";


        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("queue", queue);
        pathParams.put("tier", tier);
        pathParams.put("division", division);

        // queryparam 설정
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", "2");


        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParams(queryParams);

        URI uri = builder.buildAndExpand(pathParams).encode().toUri();


        RestTemplate restTemplate = new RestTemplate();

        // when
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("X-Riot-Token", apiKey);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);


        // then
        String body = responseEntity.getBody();


        ArrayList<LeagueEntrySaveDto> leagueEntrySaveDtos = new ArrayList<>();


        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<LeagueEntrySaveDto>>(){}.getType();

        ArrayList<LeagueEntrySaveDto> fromJson = gson.fromJson(body, type);



        System.out.println(fromJson);
        assertThat(fromJson.size()).isEqualTo(205);

    }

    @Test
    @DisplayName("잘못된 사용자일 경우 응답 404 확인 테스트")
    public void summoner404test(){

        // given
        String summonerName = "야 뚱111234";

        String url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/{summonerName}";
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("summonerName", summonerName);

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

        URI uri = builder.buildAndExpand(pathParams).encode().toUri();

        // when
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("X-Riot-Token", apiKey);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);

        try {
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);

        } catch (HttpClientErrorException e){


            assertThat(e.getRawStatusCode()).isEqualTo(404);

        }


    }

}
