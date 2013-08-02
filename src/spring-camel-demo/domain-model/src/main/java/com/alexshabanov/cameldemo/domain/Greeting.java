package com.alexshabanov.cameldemo.domain;

/**
 * Greeting demo domain object
 *
 * @author Alexander Shabanov
 */
public final class Greeting {
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

    @Override
    public String toString() {
        return "Greeting{" +
                "message='" + getMessage() + '\'' +
                ", count=" + getCount() +
                '}';
    }
}
