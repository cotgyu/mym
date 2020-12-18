package com.mym.sk;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RiotAPiTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;



    @Test
    @Description("챔피언 로테이션 api 호출이 되는 지 확인한다.")
    public void champion_rotation_api_app_test() throws Exception{
        // https://kr.api.riotgames.com/lol/platform/v3/champion-rotations?api_key=RGAPI-13c7e003-66d4-4700-b3d4-3424fdb6a607

        // given
        String apiKey = "RGAPI-13c7e003-66d4-4700-b3d4-3424fdb6a607";
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


}
