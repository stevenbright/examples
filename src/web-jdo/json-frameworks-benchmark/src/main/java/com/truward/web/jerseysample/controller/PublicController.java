package com.truward.web.jerseysample.controller;

import com.truward.web.jerseysample.model.HelloRequest;
import com.truward.web.jerseysample.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PublicController {

    @Autowired
    private HelloService helloService;


    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/hello-form", method = RequestMethod.GET)
    public ModelAndView getHello() {
        final HelloRequest helloRequest = new HelloRequest();
        helloRequest.setTitle(helloService.getDefaultTitle());
        helloRequest.setUsername(null);
        return new ModelAndView("hello-form", "command", helloRequest);
    }

    @RequestMapping(value = "/hello-form", method = RequestMethod.POST)
    public ModelAndView postHello(@ModelAttribute HelloRequest helloRequest) {
        return new ModelAndView("hello-response", "helloResponse",
                helloService.greetUser(helloRequest.getTitle(), helloRequest.getUsername()));
    }
}
