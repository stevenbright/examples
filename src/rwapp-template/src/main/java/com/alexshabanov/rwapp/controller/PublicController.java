package com.alexshabanov.rwapp.controller;

import com.alexshabanov.rwapp.model.user.UserAccount;
import com.alexshabanov.rwapp.model.user.UserProfile;
import com.alexshabanov.rwapp.model.user.UserRole;
import com.alexshabanov.rwapp.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller
public final class PublicController {
    private final Logger log = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    private HelloService helloService;

    @RequestMapping("/index.html")
    public String index(Model model) {
        final UserProfile p = new UserProfile("asd",
                Collections.<UserAccount>emptyList(), Collections.<UserRole>emptySet());
        model.addAttribute(p);

        log.info("Profile p = {}, greeting = {}", p, helloService.getGreeting("asd"));

        return "index";
    }

    @RequestMapping("/login.html")
    public String login() {
        return "login";
    }
}
