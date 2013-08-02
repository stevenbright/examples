package com.alexshabanov.cameldemo.service;

import org.springframework.stereotype.Service;

import com.alexshabanov.cameldemo.model.Hello;

import java.util.Date;

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public Hello getGreeting(String origin) {
        final Hello hello = new Hello();
        hello.setGreeting("Hello, there!");
        hello.setOrigin(origin);
        hello.setCreated(new Date());
        return hello;
    }
}