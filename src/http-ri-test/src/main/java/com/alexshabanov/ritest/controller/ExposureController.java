package com.alexshabanov.ritest.controller;

import com.alexshabanov.ritest.exposure.shared.ExposureUrls;
import com.alexshabanov.ritest.exposure.shared.GreetingRequest;
import com.alexshabanov.ritest.exposure.shared.InlineString;
import com.alexshabanov.ritest.model.WarmGreeting;
import com.alexshabanov.ritest.service.GreetingService;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

/**
 * Exposes REST API methods.
 */
@Controller
@RequestMapping("/rest/greeting")
public final class ExposureController {
    private final GreetingService greetingService;

    @Inject
    public ExposureController(GreetingService greetingService) {
        Assert.notNull(greetingService);
        this.greetingService = greetingService;
    }

    @RequestMapping(ExposureUrls.GET_HELLO)
    @ResponseBody
    public InlineString getHello(@PathVariable String subject) {
        return new InlineString(greetingService.getHello(subject));
    }

    @RequestMapping(value = ExposureUrls.CREATE_GREETING, method = RequestMethod.POST)
    @ResponseBody
    public WarmGreeting createGreeting(@RequestBody GreetingRequest request) {
        return greetingService.createGreeting(request.getOrigin(), request.getWarmLevel(), request.getSincerity());
    }
}
