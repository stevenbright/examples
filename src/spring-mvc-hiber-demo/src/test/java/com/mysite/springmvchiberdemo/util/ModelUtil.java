package com.mysite.springmvchiberdemo.util;

import com.mysite.springmvchiberdemo.model.Person;

/**
 * Helpers for working with models
 */
public final class ModelUtil {

    public static Person createPerson(String name, int age, boolean subscribedToPosts, String status) {
        final Person person = new Person();
        person.setId(0);
        person.setAge(age);
        person.setName(name);
        person.setSubscribedToPosts(subscribedToPosts);
        person.setStatus(status);
        return person;
    }

    private ModelUtil() {}
}
