package com.truward.blogboard.domain;


import java.util.Set;

public class ProfileData {
    private int id;

    private String username;

    private Set<ProfileData> referalFriends;

    private Set<ProfileData> friends;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<ProfileData> getReferalFriends() {
        return referalFriends;
    }

    public void setReferalFriends(Set<ProfileData> referalFriends) {
        this.referalFriends = referalFriends;
    }

    public Set<ProfileData> getFriends() {
        return friends;
    }

    public void setFriends(Set<ProfileData> friends) {
        this.friends = friends;
    }
}
