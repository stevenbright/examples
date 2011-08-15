package com.mysite.springmvchiberdemo.dao;

import com.mysite.springmvchiberdemo.model.Person;

import java.util.List;

public interface SampleDao {
    void deleteAll();

    void save(Person person);

    List<Person> getAll();
}
