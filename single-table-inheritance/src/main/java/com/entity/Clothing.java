package com.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Clothing")
// used for giving value in the discriminator column representing the sub class
public class Clothing extends Product {
    @Column(nullable = true)
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
