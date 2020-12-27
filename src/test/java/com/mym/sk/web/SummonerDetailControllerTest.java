package com.mym.sk.web;

import com.mym.base.controller.BaseControllerTest;
import com.mym.sk.domains.summoner.Summoner;
import com.mym.sk.domains.summoner.SummonerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@Rollback(false)
class SummonerDetailControllerTest  extends BaseControllerTest {

    @Autowired
    private SummonerRepository summonerRepository;

    @Test
    public void summonerDetailTest() throws Exception{

        // given
        String summonerName = "야 뚱";


        // when
        mockMvc.perform(get("/summoner/search/{summonerName}",summonerName)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(status().isOk());


        Summoner summoner = summonerRepository.findByName(summonerName).get();

        // then
        assertThat(summoner.getName()).isEqualTo(summonerName);

    }
}