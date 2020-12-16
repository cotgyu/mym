package com.mym.base.controller;

import com.mym.base.dto.BoardRequestDto;
import com.mym.base.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/board")
@Slf4j
public class BoardController {

    private static Logger logger = LoggerFactory.getLogger(BoardController.class);

    private final BoardService boardService;


    @GetMapping("/list/{startNum}")
    public String boardList(Model model, @PathVariable int startNum){

        List<BoardRequestDto> boardList = boardService.findboardpage(startNum);

        model.addAttribute("boardList", boardList);

        return "boardListPage";
    }

    @GetMapping("/detail/{seq}")
    public String boardDetailView(Model model, @PathVariable long seq){

        BoardRequestDto boardRequestDto = boardService.viewDetail(seq);

        model.addAttribute("board", boardRequestDto);

        return "boardDetailPage";
    }

}
