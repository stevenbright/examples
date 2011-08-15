package org.microblog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PublicController {
    private static final Logger LOG = LoggerFactory.getLogger(PublicController.class);


    @RequestMapping("/index.html")
    public String index() {
        LOG.info("Showing index!");
        return "index";
    }

    @RequestMapping("/login.html")
    public String login() {
        return "login";
    }
}
