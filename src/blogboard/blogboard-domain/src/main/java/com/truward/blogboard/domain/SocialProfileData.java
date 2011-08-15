package com.truward.blogboard.domain;

public class SocialProfileData {
    private String username;

    private boolean isFriend;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public SocialProfileData() {
    }

    public SocialProfileData(String username, boolean friend) {
        this.username = username;
        isFriend = friend;
    }
}
