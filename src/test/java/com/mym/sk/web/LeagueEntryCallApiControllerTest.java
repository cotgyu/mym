package com.mym.sk.web;

import com.mym.base.controller.BaseControllerTest;
import com.mym.sk.domains.leagueEntry.LeagueEntryRepository;
import com.mym.sk.web.dto.LeagueEntryRequestDto;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@Rollback(false)
class LeagueEntryCallApiControllerTest extends BaseControllerTest {

    @Autowired
    private LeagueEntryRepository leagueEntryRepository;

    @Test
    @DisplayName("apiCallController - entries 저장 테스트")
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