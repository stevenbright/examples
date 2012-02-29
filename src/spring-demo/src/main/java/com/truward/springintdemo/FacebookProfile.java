package com.truward.springintdemo;

public class FacebookProfile implements Profile {
    String name = "art.facebook";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatusString() {
        return "I'm on facebook!";
    }

    public int getId() {
        return 1220;
    }
}
