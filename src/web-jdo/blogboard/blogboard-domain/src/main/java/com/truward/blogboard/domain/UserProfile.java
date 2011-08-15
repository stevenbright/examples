package com.truward.blogboard.domain;

public final class UserProfile extends UserInfo {
    private String email;
    private String password;
    private boolean subscribedToPosts;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSubscribedToPosts() {
        return subscribedToPosts;
    }

    public void setSubscribedToPosts(boolean subscribedToPosts) {
        this.subscribedToPosts = subscribedToPosts;
    }
}
