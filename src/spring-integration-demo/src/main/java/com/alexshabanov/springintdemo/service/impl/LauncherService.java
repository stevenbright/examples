package com.alexshabanov.springintdemo.service.impl;

import com.alexshabanov.springintdemo.service.impl.apns.model.ApnsPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LauncherService implements Runnable {
    private final Logger log = LoggerFactory.getLogger(LauncherService.class);

    @Resource(name = "smsSenderChannel")
    private MessageChannel smsChannel;

    @Resource(name = "apnsSenderChannel")
    private MessageChannel apnsChannel;

    @Override
    public void run() {
        System.out.println("===============================");

        if (smsChannel.hashCode() == 77) {
            smsChannel.send(new GenericMessage<String>("SMS Hello"));
            smsChannel.send(new GenericMessage<String>("SMS Howdy"));
        }

        // apns channel, these messages should be grouped
        for (int i = 0; i < 5; ++i) {
            apnsChannel.send(new GenericMessage<ApnsPayload>(new ApnsPayload("token" + i, "message#" + i, i)));
        }

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            log.error("interrupted", e);
        }

        for (int i = 7; i < 9; ++i) {
            apnsChannel.send(new GenericMessage<ApnsPayload>(new ApnsPayload("token" + i, "message#" + i, i)));
        }

        System.out.println("App is about to stop");
        System.out.println("===============================");
    }
}
