package com.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Electronics")
// used for giving value in the discriminator column representing the sub class
public class Electronics extends Product{
    @Column(nullable = true)
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
