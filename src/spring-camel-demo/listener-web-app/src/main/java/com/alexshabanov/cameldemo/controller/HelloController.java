package com.alexshabanov.cameldemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.alexshabanov.cameldemo.model.Hello;
import com.alexshabanov.cameldemo.service.HelloService;

@Controller
public final class HelloController {
    private static final Logger LOG = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private HelloService helloService;

    @RequestMapping("/index.html")
    public String index(Model model) {
        final Hello hello = helloService.getGreeting("index.html");
        model.addAttribute(hello);

        LOG.info("Hello object = {}", hello);

        return "hello";
    }
}
