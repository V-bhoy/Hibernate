package com.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Electronics extends Product{
    @Column(nullable = false)
    private int warrantyPeriod;

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String toString() {
        return "Electronics{" +
                "warrantyPeriod=" + warrantyPeriod +
                "} " + super.toString();
    }
}
