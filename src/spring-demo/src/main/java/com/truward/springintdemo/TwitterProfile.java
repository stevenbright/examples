package com.truward.springintdemo;

public class TwitterProfile implements Profile {
    String name = "arty.twitter";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatusString() {
        return "I'm on twitter!";
    }

    public int getId() {
        return 24;
    }
}
