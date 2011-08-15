package com.mysite.hiberjpa.dao;

import com.mysite.hiberjpa.model.Book;

import java.util.List;


public interface AppDao {
    void deleteAll();

    void save(Book book);

    List<Book> getBooks();
}
