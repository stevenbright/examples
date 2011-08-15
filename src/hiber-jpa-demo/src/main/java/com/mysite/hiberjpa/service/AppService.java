package com.mysite.hiberjpa.service;

import com.mysite.hiberjpa.model.Book;

import java.util.List;


public interface AppService {
    void deleteAll();

    void save(Book book);

    List<Book> getBooks();
}
