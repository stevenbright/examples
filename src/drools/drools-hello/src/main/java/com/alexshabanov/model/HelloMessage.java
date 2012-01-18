package com.alexshabanov.model;


public class HelloMessage {
    public static final int HELLO = 1;
    public static final int GOODBYE = 2;

    private String message;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
