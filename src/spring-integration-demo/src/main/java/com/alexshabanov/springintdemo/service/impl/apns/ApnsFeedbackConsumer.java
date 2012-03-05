package com.alexshabanov.springintdemo.service.impl.apns;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApnsFeedbackConsumer {
    public void consumeFeedback(List<String> messages) {
        System.out.println("Klouny priehali: " + messages);
    }
}
