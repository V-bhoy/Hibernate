package com.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
// used to indicate that a class is not an entity itself,
// but its fields should be inherited by subclasses and mapped to their tables.
// the subclasses should be annotated by @Entity otherwise nothing is mapped
public abstract class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "id=" + id ;
    }
}
