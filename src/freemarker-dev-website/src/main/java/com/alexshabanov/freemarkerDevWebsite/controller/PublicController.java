package com.alexshabanov.freemarkerDevWebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller, that dispatches test pages.
 *
 * @author Alexander Shabanov
 */
@Controller
@RequestMapping("/g")
public class PublicController {

  @RequestMapping("/{pageName}")
  public ModelAndView servePage(@PathVariable("pageName") String pageName) {
    final Map<String, Object> pageModel = new HashMap<>();
    return new ModelAndView("page/" + pageName, pageModel);
  }

  @RequestMapping("/part/{pagePart}")
  public ModelAndView servePagePart(@PathVariable("pagePart") String pagePart) {
    final Map<String, Object> pageModel = new HashMap<>();
    return new ModelAndView("part/" + pagePart, pageModel);
  }
}
