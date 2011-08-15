package com.mysite.gwtspringmvc.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Sample bean class
 */
public final class SampleBean implements IsSerializable {
    private String name;

    private String comment;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
