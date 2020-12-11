package com.mym.repository;

import com.mym.controller.BaseControllerTest;
import com.mym.dto.BoardRequestDto;
import com.mym.entity.Board;
import com.mym.entity.Member;
import com.mym.entity.MemberRoles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@Rollback(false)
class BoardRepositoryTest extends BaseControllerTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;


    @PersistenceContext
    private EntityManager em;

    @Test
    public void pageBoardTest(){


        // given
        Member testMember =  Member.builder()
                .username("testMember")
                .password(("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

        memberRepository.save(testMember);

        boardRepository.save(new Board("title1", "content1", testMember));
        boardRepository.save(new Board("title2", "content2", testMember));
        boardRepository.save(new Board("title3", "content3", testMember));

        em.flush();
        em.clear();

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "seq");


        // when
        Page<Board> findBoards = boardRepository.findAll(pageRequest);

        Page<BoardRequestDto> toMap = findBoards.map(m -> new BoardRequestDto(m));

        List<BoardRequestDto> content = toMap.getContent();
        long totalElements= toMap.getTotalElements();


        assertThat(content.size()).isEqualTo(10);
        assertThat(toMap.getTotalPages()).isEqualTo(5);

    }
}