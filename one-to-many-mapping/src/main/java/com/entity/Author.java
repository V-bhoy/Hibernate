package com.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    // establishes one-to-many relationship and the book has a reference to author
    // The mappedBy represents that each book has a relation with author entity, creating a bidirectional reference
    // cascade allows operations to propagate from author to its books
    // orphan removal will directly delete the child entity when removed from the collection
    // Due to the bidirectional relation, mappedBy makes the child entity responsible for managing the relationship.
    // Hence, it would not update the foreign key to null and directly delete due to orphan removal to true
    // If orphan removal is not set to true, and we remove the child from collection, it will try
    // to update the FK to null, which can violate the constraint for nullable false,
    // if we set nullable true, the orphan children remain in the table, we need to explicitly delete them
    // The best practice is to delete the child entities from the owning entities using the orphan removal to true
    // This also an example of many to one bidirectional

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author", orphanRemoval = true)
    private List<Book> books = new ArrayList<Book>();

    @Override
    public String toString() {
       return toString(false);
    }

    public String toString(boolean showBooks){
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                (showBooks ? ", books=" + books : "")+
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
