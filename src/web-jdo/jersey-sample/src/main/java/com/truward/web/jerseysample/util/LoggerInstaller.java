package com.truward.web.jerseysample.util;

import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Handler;
import java.util.logging.LogManager;

/**
 * Performs logger inititialization in the Spring-friendly manner.
 */
public class LoggerInstaller {
    private static boolean initialized = false;

    /**
     * Initializes the logger subsystem
     */
    public static synchronized void install() {
        if (initialized) {
            return;
        }

        // Jersey uses java.util.logging - bridge to slf4 (remove all the other handlers)
        java.util.logging.Logger rootLogger = LogManager.getLogManager().getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        for (int i = 0; i < handlers.length; i++) {
            rootLogger.removeHandler(handlers[i]);
        }

        // install SLF4J bridge
        SLF4JBridgeHandler.install();
        initialized = true;
    }

    public LoggerInstaller() {
        install();
    }
}
