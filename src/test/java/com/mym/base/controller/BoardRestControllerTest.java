package com.mym.base.controller;

import com.mym.base.dto.BoardSaveDto;
import com.mym.base.entity.Board;
import com.mym.base.entity.Member;
import com.mym.base.entity.MemberRoles;
import com.mym.base.repository.BoardRepository;
import com.mym.base.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@Rollback(false)
class BoardRestControllerTest extends BaseControllerTest{

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;


    @Test
    public void restPostTest() throws Exception{

        //given
        Member testMember =  Member.builder()
                .username("testMember")
                .password(("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

        memberRepository.save(testMember);

        em.flush();
        em.clear();


        BoardSaveDto boardSaveDto = BoardSaveDto.builder()
                .title("testBoard1")
                .content("testContent1")
                .member(testMember)
                .build();


        //when then
        mockMvc.perform(post("/api/board")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(boardSaveDto))
        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("resultMessage").value("success"));
    }




    @Test
    public void restGetTest() throws Exception{

        //given
        Member testMember =  Member.builder()
                .username("testMember")
                .password(("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

        memberRepository.save(testMember);

        Board testBoard = Board.builder().title("test").content("testContent").member(testMember).build();

        boardRepository.save(testBoard);

        em.flush();
        em.clear();




        //when then
        mockMvc.perform(get("/api/board/{seq}", testBoard.getSeq())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("result").isNotEmpty());
    }


    @Test
    public void restPutTest() throws Exception{

        //given
        Member testMember =  Member.builder()
                .username("testMember")
                .password(("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

        memberRepository.save(testMember);

        Board testBoard = Board.builder().title("test").content("testContent").member(testMember).build();

        boardRepository.save(testBoard);

        em.flush();
        em.clear();


        BoardSaveDto boardUpdateDto = BoardSaveDto.builder()
                .title("updateBoard")
                .content("updateContent")
                .member(testMember)
                .build();


        //when then
        mockMvc.perform(put("/api/board/{seq}", testBoard.getSeq())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(boardUpdateDto))
        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("resultMessage").value("success"));

    }



    @Test
    public void restBadRequestPostTest() throws Exception{

        //given
        Member testMember =  Member.builder()
                .username("testMember")
                .password(("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

        memberRepository.save(testMember);

        em.flush();
        em.clear();


        BoardSaveDto boardSaveDto = BoardSaveDto.builder()
                .title("")
                .content("")
                .member(testMember)
                .build();


        //when then
        mockMvc.perform(post("/api/board")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(boardSaveDto))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }




}