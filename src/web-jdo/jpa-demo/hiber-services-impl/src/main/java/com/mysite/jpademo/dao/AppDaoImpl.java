package com.mysite.jpademo.dao;

import com.mysite.jpademo.model.Book;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

public class AppDaoImpl extends HibernateDaoSupport implements AppDao {
    public void deleteAll() {
        getHibernateTemplate().bulkUpdate("delete from Book");
        getHibernateTemplate().bulkUpdate("delete from Chapter");
    }

    public void save(Book book) {
        getHibernateTemplate().save(book);
    }

    @SuppressWarnings("unchecked")
    public List<Book> getBooks() {
        return getHibernateTemplate().find("from Book");
    }
}
