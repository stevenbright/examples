package com.mysite.jdort.model;

import javax.jdo.annotations.*;
import java.util.Date;

/**
 * Represents an account
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public final class Account implements PropertyBag {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    long id;

    String username;

    String password;

    String email;

    int age;

    boolean subscribedToNews = false;

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

    public String getStringProperty(String propertyName) {
        if ("username".equals(propertyName)) {
            return getUsername();
        } else if ("password".equals(propertyName)) {
            return getPassword();
        } else if ("email".equals(propertyName)) {
            return getEmail();
        }
        throw new IllegalArgumentException(propertyName);
    }
}
