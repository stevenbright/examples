package com.mysite.springjdbcsample.service;

import com.mysite.springjdbcsample.model.Country;

import java.util.List;

public interface CountryService {
    void save(Country country);

    List<Country> getAll();
}
