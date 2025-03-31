package com.service;

import com.entity.Author;
import com.entity.Book;
import com.repository.AuthorRepo;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private AuthorRepo authorRepo;

    public AuthorServiceImpl(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }

    @Override
    public void saveAuthor(Author author){
        try{
            authorRepo.saveAuthor(author);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in author service handler:\n",e);
        }
    }

    @Override
    public void deleteAuthor(int authorId) {
        try{
            authorRepo.deleteAuthor(authorId);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in author service handler:\n",e);
        }
    }

    @Override
    public void updateAuthor(Author author, int authorId) {
        try{
            authorRepo.updateAuthor(author, authorId);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in author service handler:\n",e);
        }
    }

    @Override
    public void removeAuthorBooks(List<Book> books, int authorId) {
        try{
           authorRepo.removeAuthorBooks(books, authorId);
        }catch(Exception e){
            throw new RuntimeException("Error occurred in author service handler:\n",e);
        }
    }


    @Override
    public Author getAuthorById(int id) {
        return  authorRepo.getAuthor(id);
    }
}
