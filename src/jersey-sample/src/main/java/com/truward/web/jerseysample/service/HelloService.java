package com.truward.web.jerseysample.service;

/**
 * Hello service interface.
 */
public interface HelloService {
    String getDefaultTitle();

    String greetUser(String title, String username);
}
