package com.mym.base.controller;

import com.mym.base.dto.MemberSaveDto;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@Rollback(false)
public class MemberControllerTest extends BaseControllerTest{

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;


    @Test
    public void querydslTest(){

        // given
        Member testMember =  Member.builder()
                .username("testMember")
                .password(("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

        Member testMember2 =  Member.builder()
                .username("testMember2")
                .password(("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

        memberRepository.save(testMember);
        memberRepository.save(testMember2);

        em.flush();
        em.clear();

        List<Member> all = memberRepository.findAll();

        // when
        List<Member> allCustomMember = memberRepository.findAllCustomMember();

        // then
        assertThat(allCustomMember.size()).isEqualTo(all.size());

    }

    @Test
    public void baseEntityTest(){
        //given
        Member testMember =  Member.builder()
                .username("testMember")
                .password(("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

        memberRepository.save(testMember);

        em.flush();
        em.clear();

        // when
        Member findMember = memberRepository.findById(1L).get();

        System.out.println(findMember.getCreatedDate());

        // then
        assertThat(findMember.getCreatedDate()).isNotNull();

    }

    @Test
    public void addMemberRestTest() throws Exception{

        //given
        MemberSaveDto memberSaveDto = MemberSaveDto.builder()
                .username("addMember")
                .password("pass")
                .build();


        //when then
        mockMvc.perform(post("/api/member")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(memberSaveDto))
        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("resultMessage").value("success"));

    }


    @Test
    public void updateMemberRestTest() throws Exception{

        //given
        Member testMember =  Member.builder()
                .username("testMember")
                .password(("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

        Member saveMember = memberRepository.save(testMember);

        em.flush();
        em.clear();

        MemberSaveDto memberSaveDto = MemberSaveDto.builder()
                .username("updateMember")
                .password("pass2")
                .build();


        //when then
        mockMvc.perform(put("/api/member/{id}", saveMember.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(memberSaveDto))
        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("result").value(saveMember.getId()));

    }

    @Test
    public void member_board_Test() {

        //given
        Member testMember =  Member.builder()
                .username("testMember")
                .password(("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

        memberRepository.save(testMember);

        Board testBoard = new Board("title", "content", testMember);
        testBoard.setMember(testMember);

        Board savedBoard = boardRepository.save(testBoard);

        em.flush();
        em.clear();

        //when
        Optional<Board> optionalBoard = boardRepository.findById(savedBoard.getSeq());
        Board findBoard = optionalBoard.get();

        //then
        assertThat(findBoard.getMember().getUsername()).isEqualTo("testMember");


    }

}
