package com.service;

import com.entity.Author;
import com.entity.Book;

import java.util.List;

public interface AuthorService {
    void saveAuthor(Author author);
    void deleteAuthor(int authorId);
    void updateAuthor(Author author, int authorId);
    void removeAuthorBooks(List<Book> books, int authorId);
    Author getAuthorById(int id);
}
