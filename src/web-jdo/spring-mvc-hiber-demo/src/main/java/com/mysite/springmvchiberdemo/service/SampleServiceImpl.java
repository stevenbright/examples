package com.mysite.springmvchiberdemo.service;

import com.mysite.springmvchiberdemo.dao.SampleDao;
import com.mysite.springmvchiberdemo.init.PreInitPool;
import com.mysite.springmvchiberdemo.model.Person;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class SampleServiceImpl implements SampleService {

    private SampleDao dao;
    public void setDao(SampleDao dao) {
        this.dao = dao;

        init();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    private void init() {
        PreInitPool.addSampleData(this);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteAll() {
        dao.deleteAll();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void save(Person person) {
        dao.save(person);
    }

    @Transactional(readOnly = true)
    public List<Person> getAll() {
        return dao.getAll();
    }
}
