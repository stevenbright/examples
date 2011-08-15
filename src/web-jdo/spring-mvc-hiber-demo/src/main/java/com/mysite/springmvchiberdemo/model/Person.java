package com.mysite.springmvchiberdemo.model;

import javax.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    private int id;

    private String name;

    private int age;

    private boolean subscribedToPosts;

    @Basic(fetch = FetchType.LAZY)
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSubscribedToPosts() {
        return subscribedToPosts;
    }

    public void setSubscribedToPosts(boolean subscribedToPosts) {
        this.subscribedToPosts = subscribedToPosts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
