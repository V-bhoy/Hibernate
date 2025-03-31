package com.service;

import com.entity.Book;

import java.util.List;

public interface BookService {
    void saveBooks(List<Book> books, int authorId);
    void updateBook(Book book, int bookId);
    Book getBook(int bookId);
}
