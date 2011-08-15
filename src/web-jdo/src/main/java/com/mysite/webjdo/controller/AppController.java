package com.mysite.webjdo.controller;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppController extends MultiActionController {
    public AppController(){
        setSupportedMethods(new String[] { "GET" });
    }

    public ModelAndView pageapp(HttpServletRequest request, HttpServletResponse response) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnsupportedOperationException("Unauthorized use of app page");
        }

        final Object principal = authentication.getPrincipal();
        if (principal == null || !(principal instanceof UserDetails)) {
            throw new UnsupportedOperationException("Principal is null or not of UserDetails class");
        }

        final UserDetails userDetails = (UserDetails) principal;

        final ModelAndView result = new ModelAndView("app");
        result.addObject("username", userDetails.getUsername());

        return result;
    }

    public ModelAndView pagehello(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("hello");
    }
}
