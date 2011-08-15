package com.truward.web.jerseysample.service;

import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    public String getDefaultTitle() {
        return "Mr.";
    }

    public String greetUser(String title, String username) {
        return "Hello, " + title + " " + username;
    }
}
