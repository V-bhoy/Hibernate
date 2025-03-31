package com.repository;

import com.entity.Author;
import com.entity.Book;
import com.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BookRepoImpl implements BookRepo {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    @Override
    public void registerBooks(List<Book> books, int authorId) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            Author author = session.get(Author.class, authorId);
            if(author != null){
                List<Book> bookList = author.getBooks();
                for(Book book : books){
                    bookList.add(book);
                }
                author.setBooks(bookList);
                session.merge(author);
            }
            tx.commit();
        }catch(Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void updateBookDetails(Book book, int bookId) {
         Transaction tx = null;
         try(Session session = sessionFactory.openSession()) {
             tx = session.beginTransaction();
             Book existingBook = session.get(Book.class, bookId);
             if(existingBook != null) {
                 existingBook.setTitle(book.getTitle());
                 session.merge(existingBook);
             }
             tx.commit();
         }catch(Exception e) {
             if(tx != null) {
                 tx.rollback();
             }
             throw e;
         }
    }

    @Override
    public Book getBookDetails(int bookId) {
         try(Session session = sessionFactory.openSession()) {
             return session.get(Book.class, bookId);
         }
    }
}
