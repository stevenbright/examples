package com.alexshabanov.spr31.service;

import com.alexshabanov.spr31.model.Hello;

public interface HelloService {
    Hello getGreeting(String origin);
}