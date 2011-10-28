package com.alexshabanov.sched.app;

import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * DI-driven application entry point.
 */
@Service(value = "appRunner")
public final class AppRunner implements Runnable {
    @Override
    public void run() {
        System.out.println("Running sched sample, press any key to exit");

        try {
            int ignored = System.in.read();
            if (ignored != -1) {
                System.out.println("OK, exiting");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
