package com.mysite.hiberjpa.service;

import com.mysite.hiberjpa.dao.AppDao;
import com.mysite.hiberjpa.model.Book;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public final class AppServiceImpl implements AppService {
    private AppDao dao;

    public void setDao(AppDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteAll() {
        dao.deleteAll();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void save(Book book) {
        dao.save(book);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooks() {
        return dao.getBooks();
    }
}
