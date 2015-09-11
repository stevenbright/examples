package com.alexshabanov.sample.brikar.website.controller;

import com.alexshabanov.sample.brikar.website.service.ArticleService;
import com.alexshabanov.sample.brikar.website.service.VisitorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Main controller that responds with user page.
 */
@Controller
@RequestMapping("/g/")
public final class PublicPageController {
  private ArticleService articleService;
  private VisitorService visitorService;

  public PublicPageController(ArticleService articleService, VisitorService visitorService) {
    this.articleService = Objects.requireNonNull(articleService, "articleService");
    this.visitorService = Objects.requireNonNull(visitorService, "visitorService");
  }

  @RequestMapping("/index")
  public String index() {
    visitorService.incVisitor("Index");
    return "page/index";
  }

  @RequestMapping("/articles")
  public ModelAndView articles() {
    visitorService.incVisitor("Articles");
    return new ModelAndView("page/articles", "articles", articleService.getArticles());
  }

  @RequestMapping("/about")
  public String about() {
    visitorService.incVisitor("About");
    return "page/about";
  }

  @RequestMapping("/login")
  public ModelAndView login(@RequestParam(value = "error", required = false) String loginError) {
    final Map<String, Object> params = new HashMap<>();
    params.put("loginError", loginError);
    params.put("currentTime", System.currentTimeMillis());
    return new ModelAndView("page/login", params);
  }
}
