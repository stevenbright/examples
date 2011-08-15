package com.mysite.springmvchiberdemo.dao;

import com.mysite.springmvchiberdemo.model.Person;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

public class SampleDaoImpl extends HibernateDaoSupport implements SampleDao {
    public void deleteAll() {
        getHibernateTemplate().bulkUpdate("delete from Person");
    }

    public void save(Person person) {
        getHibernateTemplate().save(person);
    }

    @SuppressWarnings("unchecked")
    public List<Person> getAll() {
        return getHibernateTemplate().find("from Person");
    }
}
