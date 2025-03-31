package com.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    // the many-to-one relation establishes bidirectional relationship with the parent entity
    // the join column represents the foreign key in the child entity.
    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Author author;

    @Override
    public String toString() {
       return toString(false);
    }

    public String toString( boolean showAuthor){
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                (showAuthor ? ", author=" + author : "")+
                '}';
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
