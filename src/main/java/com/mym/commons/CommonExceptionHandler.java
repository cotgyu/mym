package com.mym.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class CommonExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

    public static final String DEFAULT_ERROR_VIEW = "exception";

    @ExceptionHandler(value = RuntimeException.class)
    public ModelAndView defaultRuntimeErrorHandler(HttpServletRequest req, RuntimeException e) {
        logger.error("RuntimeException: " + e);
        logger.error("url: " + req.getRequestURL());

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);

        return mav;

    }

}
