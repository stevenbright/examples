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
@Service(value = "schedService")
public class SchedService {
    private final Logger log = LoggerFactory.getLogger(SchedService.class);

    private static final int MAX_ITERATIONS = 1000;

    private int iterationCounter = 0;

    private final BlockingQueue<Integer> deliveryQueue = new ArrayBlockingQueue<Integer>(2);

//    @Scheduled(fixedDelay = 800)
    public void enqueueSomething() {
        ++iterationCounter;
        if (iterationCounter > MAX_ITERATIONS) {
            return;
        }

        send(iterationCounter * 3);
        send(iterationCounter * 3 + 1);
        send(iterationCounter * 3 + 2);
    }

    private void send(Integer deliveryTarget) {
        log.info("{}: sending {}", Thread.currentThread(), deliveryTarget);

        try {
            deliveryQueue.put(deliveryTarget);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("interrupted", e);
        }
    }

//    @Scheduled(fixedDelay = 400)
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
                log.info("{}: delivering {}", Thread.currentThread(), deliveryTarget);
            }
        } while (deliveryTarget != null);
    }
}
