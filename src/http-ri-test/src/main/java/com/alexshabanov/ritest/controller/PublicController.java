package com.alexshabanov.ritest.controller;

import com.alexshabanov.ritest.model.WarmGreeting;
import com.alexshabanov.ritest.service.GreetingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public final class PublicController {
    private final GreetingService greetingService;

    public PublicController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @RequestMapping("/index.html")
    public String index(Model model) {
        final String greetingServiceTarget = "GreetingServiceTarget: " + greetingService.getClass().getSimpleName();
        model.addAttribute("greetingServiceTarget", greetingServiceTarget);
        return "index";
    }

    @RequestMapping(value = "/invoke/get-hello.do", method = RequestMethod.POST)
    public ModelAndView getHello(@RequestParam("subject") String subject) {
        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("subject", subject);
        result.put("result", greetingService.getHello(subject));
        return new ModelAndView("result-table", "result", result);
    }

    @RequestMapping(value = "/invoke/create-greeting.do", method = RequestMethod.POST)
    public ModelAndView createGreeting(@RequestParam("origin") String origin,
                                       @RequestParam("warm-level") int warmLevel,
                                       @RequestParam("sincerity") double sincerity) {

        final WarmGreeting greeting = greetingService.createGreeting(origin, warmLevel, sincerity);

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("origin", greeting.getOrigin());
        result.put("created", greeting.getCreated());
        result.put("greeting", greeting.getGreeting());
        result.put("quality", greeting.getQuality());
        return new ModelAndView("result-table", "result", result);
    }
}
