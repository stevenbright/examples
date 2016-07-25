package com.alexshabanov.brikarrestdemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Alexander Shabanov
 */
@Controller
@RequestMapping("rest/hello")
public final class HelloController {

  @RequestMapping("{name}")
  @ResponseBody
  public ResponseEntity<String> hello(@PathVariable String name) {
    final String content = "Hello " + name + " @ "  + System.currentTimeMillis();
    return new ResponseEntity<>(content, HttpStatus.OK);
  }
}
