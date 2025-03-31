package com.repository;

import com.entity.Author;
import com.entity.Book;
import com.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthorRepoImpl implements AuthorRepo {
  SessionFactory sf = HibernateUtil.getSessionFactory();
    @Override
    public void saveAuthor(Author author) {
        Transaction tx = null;
        try(Session session = sf.openSession()) {
           tx = session.beginTransaction();
           session.persist(author);
           tx.commit();
        }catch(Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void deleteAuthor(int authorId) {
          Transaction tx = null;
          try(Session session = sf.openSession()) {
              tx = session.beginTransaction();
              Author author = session.get(Author.class, authorId);
              if(author != null) {
                  session.remove(author);
              }
              tx.commit();
          }catch (Exception e) {
              if(tx != null) {
                  tx.rollback();
              }
              throw e;
          }
    }

    @Override
    public void updateAuthor(Author author, int authorId) {
        Transaction tx = null;
        try(Session session = sf.openSession()) {
            tx = session.beginTransaction();
            Author existingAuthor = session.get(Author.class, authorId);
            if(existingAuthor != null) {
                existingAuthor.setName(author.getName());
                session.merge(existingAuthor);
            }
            tx.commit();
        }catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void removeAuthorBooks(List<Book> books, int authorId) {
        Transaction tx = null;
        try(Session session = sf.openSession()) {
            tx = session.beginTransaction();
            Author existingAuthor = session.get(Author.class, authorId);
            if(existingAuthor != null) {
                List<Book> currBooks = existingAuthor.getBooks();
                Set<Integer> bookIdsToRemove = books.stream()
                        .map(Book::getId)
                        .collect(Collectors.toSet());
                currBooks.removeIf(book -> bookIdsToRemove.contains(book.getId()));
            }
            tx.commit();
        }catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }


    @Override
    public Author getAuthor(int authorId) {
        try(Session session = sf.openSession()) {
            Author author = session.get(Author.class, authorId);
            if(author != null) {
                Hibernate.initialize(author.getBooks());
            }
            return author;
        }
    }
}
