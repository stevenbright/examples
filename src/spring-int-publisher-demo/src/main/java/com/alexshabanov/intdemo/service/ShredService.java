package com.alexshabanov.intdemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShredService {
    private int feedbackCounter;

    private final Logger logger = LoggerFactory.getLogger(ShredService.class);

    public void sayHello() {
        System.out.println("We've met before, haven't we?");
    }

    public String fetchFeedback() {
        logger.info("Fetch feedback, t={}", System.currentTimeMillis());
        return "Feedback#" + (feedbackCounter++);
    }

    public void fireFeedback(String feedback) {
        logger.info("Fire feedback, t={}", System.currentTimeMillis());
        System.out.println("Firing feedback: " + feedback);
    }
}
