package com.alexshabanov.springintdemo.service.impl.apns;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ApnsFeedbackProvider {
    private final AtomicInteger counter = new AtomicInteger(1);

    public List<String> provideFeedback() {
        final int value = counter.incrementAndGet();
        if (value % 3 == 0) {
            return Collections.emptyList();
        }

        System.out.println("Klouny uehali, value = " + value + " time: " + System.currentTimeMillis());
        return Arrays.asList("FOO_" + value, "BAR_" + value, "BAZ_" + value);
    }
}
