package com.mysite.hiberjpa.dao;

import com.mysite.hiberjpa.model.Book;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

public class AppDaoImpl extends HibernateDaoSupport implements AppDao {
    public void deleteAll() {
        getHibernateTemplate().bulkUpdate("delete from Chapter");
        getHibernateTemplate().bulkUpdate("delete from Book");
    }

    public void save(Book book) {
        getHibernateTemplate().save(book);
    }

    @SuppressWarnings("unchecked")
    public List<Book> getBooks() {
        return getHibernateTemplate().find("from Book");
    }
}
