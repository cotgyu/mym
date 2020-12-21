package com.mym.yd.controller;

import com.mym.base.controller.BaseControllerTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SummonerControllerTest extends BaseControllerTest {

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mockMvc.perform(get("/summoner/selectTier"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }


}