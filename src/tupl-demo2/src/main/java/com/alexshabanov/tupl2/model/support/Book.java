package com.alexshabanov.tupl2.model.support;

/**
 * Book domain model.
 */
public final class Book extends AbstractBook<Author, Genre> {

  public Book() {
  }

  public Book(BookUpdate bookUpdate) {
    setId(bookUpdate.getId());
    setTitle(bookUpdate.getTitle());
    setIsbn(bookUpdate.getIsbn());
    setPages(bookUpdate.getPages());
  }
}
