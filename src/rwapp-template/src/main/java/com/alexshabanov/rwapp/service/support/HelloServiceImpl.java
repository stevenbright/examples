package com.alexshabanov.rwapp.service.support;

import com.alexshabanov.rwapp.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String getGreeting(String origin) {
        return "Hello, " + origin;
    }
}