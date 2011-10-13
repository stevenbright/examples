package com.alexshabanov.sched.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Scheduled tasks.
 */
@Service
public final class SchedService {
    private final Logger log = LoggerFactory.getLogger(SchedService.class);

    private static final int MAX_ITERATIONS = 5;

    private int iterationCounter = 0;

    private final BlockingQueue<Integer> deliveryQueue = new ArrayBlockingQueue<Integer>(2);

    @Scheduled(fixedDelay = 2 * 1000)
    public void enqueueSomething() {
        ++iterationCounter;
        if (iterationCounter > MAX_ITERATIONS) {
            return;
        }

        send(iterationCounter * 2);
        send(iterationCounter * 2 + 1);
    }

    private void send(Integer deliveryTarget) {
        log.info("sending {}", deliveryTarget);

        try {
            deliveryQueue.put(deliveryTarget);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("interrupted", e);
        }
    }

    @Scheduled(fixedDelay = 2 * 500)
    public void dequeueSomething() {
        Integer deliveryTarget = null;
        do {
            try {
                deliveryTarget = deliveryQueue.poll(10, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (deliveryTarget != null) {
                // do something with the delivery target
                log.info("delivering {}", deliveryTarget);
            }
        } while (deliveryTarget != null);
    }
}
