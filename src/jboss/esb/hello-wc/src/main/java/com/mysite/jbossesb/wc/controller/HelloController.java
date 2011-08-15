package com.mysite.jbossesb.wc.controller;

import com.mysite.jbossesb.wc.model.Hello;
import com.mysite.jbossesb.wc.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Represents controller that greets user with the warm message.
 */
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

        return "index";
    }

    @RequestMapping("/index.do")
    public String indexDo(Model model) {
        final Hello hello = helloService.getGreeting("index.do");
        model.addAttribute(hello);

        LOG.info("Hello do object = {}", hello);

        return "index";
    }
}
