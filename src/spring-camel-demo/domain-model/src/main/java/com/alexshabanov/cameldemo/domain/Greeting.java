package com.alexshabanov.cameldemo.domain;

import java.io.Serializable;

/**
 * Greeting demo domain object
 *
 * @author Alexander Shabanov
 */
public final class Greeting implements Serializable {
    private static final long serialVersionUID = 1001L;

    private final String message;
    private final int count;

    public Greeting(String message, int count) {
        this.message = message;
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public int getCount() {
        return count;
    }
}
