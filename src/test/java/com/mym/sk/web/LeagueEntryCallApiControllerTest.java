package com.mym.sk.web;

import com.mym.base.controller.BaseControllerTest;
import com.mym.sk.domains.leagueEntry.LeagueEntryRepository;
import com.mym.sk.web.dto.LeagueEntryRequestDto;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@Rollback(false)
class LeagueEntryCallApiControllerTest extends BaseControllerTest {

    @Autowired
    private LeagueEntryRepository leagueEntryRepository;

    @Test
    @Description("apiCallController 테스트")
    public void entriesApiCallAndCheckResultSizeTest() throws Exception {

        //given
        String division = "I";
        String tier = "DIAMOND";
        String queue = "RANKED_SOLO_5x5";

        LeagueEntryRequestDto leagueEntryRequestDto = LeagueEntryRequestDto.builder()
                .queue(queue)
                .tier(tier)
                .division(division)
                .page(1).build();


        // when
        mockMvc.perform(post("/league/entries")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(leagueEntryRequestDto)))
                .andExpect(status().isOk());


        //then
        int size = leagueEntryRepository.findAll().size();

        assertThat(size).isEqualTo(205);
    }


}