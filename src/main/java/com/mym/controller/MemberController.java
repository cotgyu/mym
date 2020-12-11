package com.mym.controller;

import com.mym.dto.MemberRequestDto;
import com.mym.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/member")
@Slf4j
public class MemberController {

    private static Logger logger = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;

    @GetMapping("/list")
    public String memberList(Model model){

        logger.error("MemberController - memberList 조회");

        model.addAttribute("memberList", memberService.findAllMember());

        return "memberListPage";
    }

    @RequestMapping("/loginCheck")
    @ResponseBody
    public boolean loginCheck(String username, HttpSession session) throws Exception {
        boolean checkMember = false;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User authenticationUser = (User)authentication.getPrincipal();

        if(authenticationUser == null) {
            return false;
        }

        if(authenticationUser.getUsername().equals(username)){
            MemberRequestDto member = memberService.getMemberByUsername(username);

            if(member != null){
                session.setAttribute("member", member);
                session.setAttribute("username", member.getUsername());

                checkMember = true;
            }
        }

        return checkMember;
    }

}
