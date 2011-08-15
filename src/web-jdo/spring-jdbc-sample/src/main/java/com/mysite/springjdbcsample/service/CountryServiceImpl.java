package com.mysite.springjdbcsample.service;

import com.mysite.springjdbcsample.dao.CountryDao;
import com.mysite.springjdbcsample.model.Country;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CountryServiceImpl implements CountryService {

    private CountryDao dao;

    public void setDao(CountryDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void save(Country country) {
        dao.save(country);
    }

    @Transactional(readOnly = true)
    public List<Country> getAll() {
        return dao.getAll();
    }
}
