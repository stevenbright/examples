package com.mysite.springmvchiberdemo.init;

import com.mysite.springmvchiberdemo.model.Person;
import com.mysite.springmvchiberdemo.service.SampleService;

/**
 * Service with pre-initialized data pool
 */
public final class PreInitPool {

    private static Person createPerson(String name, int age, boolean subscribedToPosts, String status) {
        final Person person = new Person();

        person.setName(name);
        person.setAge(age);
        person.setSubscribedToPosts(subscribedToPosts);
        person.setStatus(status);

        return person;
    }

    public static void addSampleData(SampleService service) {
        service.save(createPerson("alice", 19, true, "Alice's sample status"));
        service.save(createPerson("bob", 25, false, "This is the Bob's status"));
        service.save(createPerson("cavin", 39, true, "No status"));
        service.save(createPerson("dave", 38, true, "Hi there!"));
        service.save(createPerson("ed", 72, false, "It's me"));
    }

    private PreInitPool() {}
}
