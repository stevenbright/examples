package com.alexshabanov.cameldemo.service;

import com.alexshabanov.cameldemo.model.Hello;

public interface HelloService {
    Hello getGreeting(String origin);
}