package com.mysite.gwtspringmvc.server.controllers;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginController extends AbstractController {
    private final Logger log = Logger.getLogger(LoginController.class);

    public LoginController() {
        setSupportedMethods(new String[] { "GET" });
    }
    
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("handleRequestInternal");
        }

        return new ModelAndView("login");
    }
}
