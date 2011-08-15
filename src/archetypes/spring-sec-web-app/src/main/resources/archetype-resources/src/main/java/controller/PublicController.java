package ${package}.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import ${package}.model.Hello;
import ${package}.service.HelloService;

import java.util.Date;

/**
 * Represents controller that greets user with the warm message.
 */
@Controller
public final class PublicController {
    private static final Logger LOG = LoggerFactory.getLogger(PublicController.class);


    @Autowired
    private HelloService helloService;


    @RequestMapping("/hello.html")
    public String index(Model model) {
        final Hello hello = helloService.getGreeting("index.html");
        model.addAttribute(hello);

        LOG.info("Hello object = {}", hello);

        return "hello";
    }

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
