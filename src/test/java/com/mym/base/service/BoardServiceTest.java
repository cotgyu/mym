package com.mym.base.service;

import com.mym.base.controller.BaseControllerTest;
import com.mym.base.dto.BoardSaveDto;
import com.mym.base.entity.Board;
import com.mym.base.entity.Member;
import com.mym.base.entity.MemberRoles;
import com.mym.base.repository.BoardRepository;
import com.mym.base.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@Rollback(false)
class BoardServiceTest extends BaseControllerTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void addBoardTest(){

        //given
        Member testMember =  Member.builder()
                .username("testMember")
                .password(("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

        memberRepository.save(testMember);

        BoardSaveDto testDto = new BoardSaveDto("title","content", testMember);

        //when
        Long boardSeq = boardService.addBoard(testDto);
        Optional<Board> findBoard = boardRepository.findById(boardSeq);


        //then
        Board result = findBoard.get();
        assertThat(result.getTitle()).isEqualTo("title");
    }


    @Test
    public void updateBoardTest(){
        Member testMember =  Member.builder()
                .username("testMember")
                .password(("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

        memberRepository.save(testMember);

        BoardSaveDto testDto = new BoardSaveDto("title","content", testMember);
        long boardSeq = boardService.addBoard(testDto);


        //when
        BoardSaveDto updateDto = new BoardSaveDto("updateTitle", "updateContent", testMember);
        boardService.updateBoard(boardSeq, updateDto);

        Optional<Board> findBoard = boardRepository.findById(boardSeq);


        //then
        Board result = findBoard.get();
        assertThat(result.getTitle()).isEqualTo("updateTitle");
    }

}