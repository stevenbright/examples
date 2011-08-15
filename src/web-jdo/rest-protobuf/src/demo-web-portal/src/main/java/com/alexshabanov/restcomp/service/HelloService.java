package com.alexshabanov.restcomp.service;

import com.alexshabanov.restcomp.model.Hello;

/**
 * Hello business service.
 */
public interface HelloService {
    Hello getGreeting(String origin);
}