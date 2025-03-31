package com.repository;

import com.entity.Book;

import java.util.List;

public interface BookRepo {
    void registerBooks(List<Book> books, int authorId);
    void updateBookDetails(Book book, int bookId);
    // deleting child entities directly is not a good practice,
    // it can lead to inconsistent data in the parent entity
    Book getBookDetails(int bookId);
}
