package com.mysite.gdatatest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public final class PublicController {

    @RequestMapping("/main.html")
    public String main() {
        return "public/main";
    }
}
