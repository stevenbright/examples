package com.mysite.jdosample.model;

import javax.jdo.annotations.*;
import java.util.Date;

/**
 * Represents an account
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Account {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    long id;

    String username;

    String password;

    String email;

    int age;

    boolean subscribedToNews = false;

    double latitude;

    double longitude;

    Date created = null;

    Date updated = null;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSubscribedToNews() {
        return subscribedToNews;
    }

    public void setSubscribedToNews(boolean subscribedToNews) {
        this.subscribedToNews = subscribedToNews;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
