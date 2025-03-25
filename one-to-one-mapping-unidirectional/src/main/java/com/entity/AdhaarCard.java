package com.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "adhaar")
public class AdhaarCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "aadhar_no", nullable = false, unique = true, length = 12)
    private String adhaarNo;

    @Override
    public String toString() {
        return "AdhaarCard{" +
                "id=" + id +
                ", adhaarNo='" + adhaarNo + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getAdhaarNo() {
        return adhaarNo;
    }

    public void setAdhaarNo(String adhaarNo) {
        this.adhaarNo = adhaarNo;
    }
}
