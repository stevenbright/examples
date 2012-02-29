package com.truward.springintdemo;

public class GoogleAccount implements Account {
    private Profile profile;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getLogin() {
        return "arthur.google";
    }
}
