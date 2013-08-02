package com.alexshabanov.cameldemo.listener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public final class PublicController {

    @RequestMapping("/index.html")
    public String index() {
        return "index";
    }
}
