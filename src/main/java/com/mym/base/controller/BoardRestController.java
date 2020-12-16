package com.mym.base.controller;

import com.mym.base.dto.BoardRequestDto;
import com.mym.base.dto.BoardSaveDto;
import com.mym.base.entity.Member;
import com.mym.base.entity.MemberRoles;
import com.mym.base.repository.MemberRepository;
import com.mym.base.service.BoardService;
import com.mym.base.validator.BoardValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/board")
public class BoardRestController {

    private static Logger logger = LoggerFactory.getLogger(BoardRestController.class);

    private final BoardService boardService;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final BoardValidator boardValidator;

    @PostMapping
    public ResponseEntity addBoard(@RequestBody BoardSaveDto dto, Errors errors) throws Exception{
        Map<String, Object> resultMap = new HashMap<>();

        boardValidator.validate(dto, errors);


        if(errors.hasErrors()){
            logger.error("addBoard - 잘못된 요청입니다.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        long result = boardService.addBoard(dto);

        resultMap.put("result", result);
        resultMap.put("resultMessage", "success");

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @GetMapping(value = "{seq}")
    public ResponseEntity getBoard(@PathVariable Long seq){
        Map<String, Object> resultMap = new HashMap<>();

        BoardRequestDto boardDto = boardService.viewDetail(seq);

        resultMap.put("result", boardDto);

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }


    @PutMapping(value = "/{seq}")
    public ResponseEntity updateBoard(@PathVariable Long seq,
                                      @RequestBody BoardSaveDto dto, Errors errors) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();



        boardValidator.validate(dto, errors);

        if(errors.hasErrors()){
            logger.error("updateBoard - 잘못된 요청입니다.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        long result = boardService.updateBoard(seq, dto);

        if(result < 0){
            resultMap.put("result", result);
            resultMap.put("resultMessage", "fail");

            return new ResponseEntity(resultMap, HttpStatus.BAD_REQUEST);
        }

        resultMap.put("result", result);
        resultMap.put("resultMessage", "success");


        return new ResponseEntity(resultMap, HttpStatus.OK);
    }


    // jpa create 옵션에 따른 초기데이터 생성 - 운영 시엔 옵션 변경할 것
    @PostConstruct
    public void testInit(){

        Member testMember =  Member.builder()
                .username("testMember")
                .password(passwordEncoder.encode("pass"))
                .roles(Set.of(MemberRoles.USER))
                .build();

        Member testAdmin =  Member.builder()
                .username("testAdmin")
                .password(passwordEncoder.encode("pass"))
                .roles(Set.of(MemberRoles.ADMIN))
                .build();


        memberRepository.save(testMember);
        memberRepository.save(testAdmin);


        for(int i=1; i<40; i++ ) {

            boardService.addBoard(new BoardSaveDto("title"+i, "content"+i, testMember));

        }

    }

}
