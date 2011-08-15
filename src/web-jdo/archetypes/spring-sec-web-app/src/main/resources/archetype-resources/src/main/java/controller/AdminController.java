package ${package}.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Represents controller that encapsulates all the admin pages.
 */
@Controller
public final class AdminController {

    @RequestMapping("/admin/main.html")
    public String index() {
        return "admin/main";
    }
}
