package com.alexshabanov.rwapp.controller;

import com.alexshabanov.rwapp.model.user.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.alexshabanov.rwapp.model.Hello;
import com.alexshabanov.rwapp.service.HelloService;

@Controller
public final class PublicController {
    private static final Logger LOG = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    private HelloService helloService;

    @RequestMapping("/index.html")
    public String index(Model model) {
        final Hello hello = helloService.getGreeting("index.html");
        model.addAttribute(hello);
        
        final UserProfile p = new UserProfile();

        LOG.info("Hello object = {}, p = {}", hello, p);

        return "hello";
    }

    @RequestMapping("/login.html")
    public String login() {
        return "login";
    }
}
