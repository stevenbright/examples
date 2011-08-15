package com.mysite.jpademo.service;

import com.mysite.jpademo.model.Book;

import java.util.List;


public interface AppService {
    void deleteAll();

    void save(Book book);

    List<Book> getBooks();
}
