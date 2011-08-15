package com.truward.serdemo.beans;


import java.io.Serializable;

public class Profile implements Serializable {
    private int id;
    private String username;
    private String passwordHash;
    private String email;
    private boolean subscribedToNews;
    private int age;

    

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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSubscribedToNews() {
        return subscribedToNews;
    }

    public void setSubscribedToNews(boolean subscribedToNews) {
        this.subscribedToNews = subscribedToNews;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", email='" + email + '\'' +
                ", subscribedToNews=" + subscribedToNews +
                ", age=" + age +
                '}';
    }
}
