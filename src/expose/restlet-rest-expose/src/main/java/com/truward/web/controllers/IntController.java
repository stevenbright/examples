package com.truward.web.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Properties;

@Controller
public class IntController {

    @Autowired
    private Properties appProperties;

    @RequestMapping("/index.html")
    public String index() {
        return "index";
    }

    @RequestMapping("/hello.html")
    public ModelAndView helloWorld() {
        return new ModelAndView("hello", "messages", new String[] {
                "Hello from IntController!",
                appProperties.getProperty("helloMessage")
        });
    }
}
