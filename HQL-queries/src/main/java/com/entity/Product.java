package com.entity;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="products")
public class Product extends Base {
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private int price;
    @OneToOne(mappedBy = "product")
    private Customer customer;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
       return toString(false);
    }

    public String toString(boolean showCustomer){
        return "Product{" + super.toString() +
                "productName='" + productName + '\'' +
                ", price=" + price +
                (showCustomer ? ", customer=" + customer : "") +
                "} ";
    }
}
