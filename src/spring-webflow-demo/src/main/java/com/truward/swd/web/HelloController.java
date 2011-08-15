package com.truward.swd.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public final class HelloController {
    private final Logger log = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("/hello")
    public ModelAndView helloWorld() {
        log.info("Hello world issued");
        String message = "Hello World, Spring 3.0!";
        return new ModelAndView("hello", "message", message);
    }
}
