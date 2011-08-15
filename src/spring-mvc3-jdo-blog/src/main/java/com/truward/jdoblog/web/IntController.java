package com.truward.jdoblog.web;


import com.truward.jdoblog.services.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IntController {

    @Autowired
    private BlogService blogService;

    @RequestMapping("/hello")
    public ModelAndView helloWorld() {
        String message = "Hello World, Spring 3.0!";
        return new ModelAndView("hello", "message", message);
    }

    @RequestMapping("/scaffolding")
    public String scaffolding() {
        return "scaffolding/index";
    }

    @RequestMapping("/accounts")
    public String accounts(Model model) {
        model.addAttribute("message", "Accounts placeholder");
        model.addAttribute("accounts", blogService.getAccounts());
        return "scaffolding/accounts";
    }
}
