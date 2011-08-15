package com.mysite.springmvchiberdemo.service;

import com.mysite.springmvchiberdemo.model.Person;

import java.util.List;

public interface SampleService {
    void deleteAll();

    void save(Person person);

    List<Person> getAll();
}
