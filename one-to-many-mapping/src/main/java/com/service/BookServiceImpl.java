package com.service;

import com.entity.Book;
import com.repository.BookRepo;

import java.util.List;

public class BookServiceImpl implements BookService{
    private BookRepo bookRepo;

    public BookServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }
    @Override
    public void saveBooks(List<Book> books, int authorId) {
        try{
            bookRepo.registerBooks(books, authorId);
        }catch(Exception e){
            throw new RuntimeException("Error occured in book service handler", e);
        }
    }

    @Override
    public void updateBook(Book book, int bookId) {
        try{
            bookRepo.updateBookDetails(book, bookId);
        }catch(Exception e){
            throw new RuntimeException("Error occured in book service handler", e);
        }
    }

    @Override
    public Book getBook(int bookId) {
        return bookRepo.getBookDetails(bookId);
    }
}
