package com.alexshabanov.springrmi.service;

import com.alexshabanov.springrmi.model.Hello;

/**
 * Hello business service.
 */
public interface HelloService {
    Hello getGreeting(String origin);
}