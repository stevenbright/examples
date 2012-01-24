package com.alexshabanov.rwapp.service;

import com.alexshabanov.rwapp.model.Hello;

public interface HelloService {
    Hello getGreeting(String origin);
}