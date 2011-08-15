package ${package}.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public final class ProfileController {

    @RequestMapping("/profile/view.html")
    public String index() {
        return "profile/view";
    }
}
