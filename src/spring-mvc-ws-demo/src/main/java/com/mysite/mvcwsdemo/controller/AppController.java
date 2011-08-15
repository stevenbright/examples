package com.mysite.mvcwsdemo.controller;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Application Controller.
 */
public class AppController extends MultiActionController {
    private final Logger log = Logger.getLogger(AppController.class);

    public ModelAndView getindex(HttpServletRequest request, HttpServletResponse response) {
        if (log.isTraceEnabled()) {
            log.trace("GET index, request=" + request + ", response=" + response);
        }

        return new ModelAndView("index");
    }

    public ModelAndView gethello(HttpServletRequest request, HttpServletResponse response){
        if (log.isTraceEnabled()) {
            log.trace("GET hello, request=" + request + ", response=" + response);
        }

        return new ModelAndView("hello");
    }
}

