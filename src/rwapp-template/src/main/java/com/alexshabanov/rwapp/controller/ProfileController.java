package com.alexshabanov.rwapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller
public final class ProfileController {

    @RequestMapping("/profile/index.html")
    String profile(Model model) {
        model.addAttribute(Collections.singletonMap("id", 12354));
        return "profile/index";
    }
}
