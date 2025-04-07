package com.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Clothing extends Product {
    @Column(nullable = false)
    private String size;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Clothing{" +
                "size='" + size + '\'' +
                "} " + super.toString();
    }
}
