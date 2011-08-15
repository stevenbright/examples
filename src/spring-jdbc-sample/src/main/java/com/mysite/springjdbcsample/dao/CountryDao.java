package com.mysite.springjdbcsample.dao;

import com.mysite.springjdbcsample.model.Country;

import java.util.List;

public interface CountryDao {
    void save(Country country);

    List<Country> getAll();
}
