package com.alexshabanov.ritest.service;

import com.alexshabanov.ritest.model.WarmGreeting;

public interface GreetingService {
    String getHello(String subject);

    WarmGreeting createGreeting(String origin, int warmLevel, double sincerity);
}