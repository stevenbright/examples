package com.alexshabanov.rwapp.controller;

import com.alexshabanov.rwapp.model.security.UserWithId;
import com.alexshabanov.rwapp.model.user.UserProfile;
import com.alexshabanov.rwapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public final class ProfileController {
    @Autowired
    private UserService userService;

    @RequestMapping("/profile/index.html")
    String profile(Model model) {
        final UserWithId principal = (UserWithId) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final UserProfile profile = userService.findProfile(principal.getUserId());
        model.addAttribute("profile", profile);
        return "profile/index";
    }
}
