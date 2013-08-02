package com.alexshabanov.cameldemo.listener.controller;

import com.alexshabanov.cameldemo.listener.service.GreetingSinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public final class PublicController {

    @Autowired
    private GreetingSinkService greetingSinkService;

    @RequestMapping("/index.html")
    public String index() {
        return "index";
    }
}
