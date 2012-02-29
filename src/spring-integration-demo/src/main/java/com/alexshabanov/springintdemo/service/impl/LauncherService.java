package com.alexshabanov.springintdemo.service.impl;

import com.alexshabanov.springintdemo.service.CommService;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LauncherService implements Runnable {
    @Resource
    private CommService commService;

    @Resource(name = "smsChannel")
    private MessageChannel smsChannel;

    @Override
    public void run() {
        System.out.println("===============================");
        System.out.println("comm service timeout: " + commService.getTimeoutMillis());

        smsChannel.send(new GenericMessage<String>("Hello"));
        smsChannel.send(new GenericMessage<String>("Howdy"));

        System.out.println("App is about to stop");
        System.out.println("===============================");
    }
}
