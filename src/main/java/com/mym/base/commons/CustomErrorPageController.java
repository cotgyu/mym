package com.mym.base.commons;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorPageController implements ErrorController {
    private static final String ERROR_PAGE = "/error";

    @Override
    public String getErrorPath() {
        return ERROR_PAGE;
    }

    @RequestMapping("/error")
    public String errorPage(HttpServletRequest request, Model model){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        model.addAttribute("code", status.toString());

        return "exception";
    }

}
