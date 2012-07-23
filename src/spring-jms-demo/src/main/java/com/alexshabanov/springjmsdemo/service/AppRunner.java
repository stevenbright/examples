package com.alexshabanov.springjmsdemo.service;

import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("appRunner")
public class AppRunner implements Runnable {

    @Inject
    private JmsMessageListener client;


    @Override
    public void run() {
        System.out.println("I'm spinning!");
    }
}
