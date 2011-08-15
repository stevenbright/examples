package com.mysite.jpademo.dao;

import com.mysite.jpademo.model.Book;

import java.util.List;


public interface AppDao {
    void deleteAll();

    void save(Book book);

    List<Book> getBooks();
}
