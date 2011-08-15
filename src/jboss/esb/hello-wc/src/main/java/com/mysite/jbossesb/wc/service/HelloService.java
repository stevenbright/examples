package com.mysite.jbossesb.wc.service;

import com.mysite.jbossesb.wc.model.Hello;

/**
 * Hello business service.
 */
public interface HelloService {
    Hello getGreeting(String origin);
}