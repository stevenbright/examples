package com.mysite.springmvchiberdemo.controller;

import com.mysite.springmvchiberdemo.model.Person;
import com.mysite.springmvchiberdemo.service.SampleService;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class AppController extends MultiActionController {
    private final Logger log = Logger.getLogger(AppController.class);

    private SampleService sampleService;

    public void setSampleService(SampleService sampleService) {
        this.sampleService = sampleService;
    }

    public ModelAndView getindex(HttpServletRequest request, HttpServletResponse response) {
        if (log.isInfoEnabled()) {
            log.info("GET index, request=" + request + ", response=" + response);
        }

        final List<Person> persons = sampleService.getAll();
        return new ModelAndView("index",
                "persons", persons);
    }

    public ModelAndView gethello(HttpServletRequest request, HttpServletResponse response){
        if (log.isInfoEnabled()) {
            log.info("GET hello, request=" + request + ", response=" + response);
        }

        return new ModelAndView("hello");
    }
}
