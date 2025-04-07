package com.entity;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

@Entity
@Table(name="customers")
public class Customer extends Base{
    @Column(nullable = false)
    private String name;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Product product;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                (Hibernate.isInitialized(product) ? ", product=" + product  : "")+
                '}';
    }

}
