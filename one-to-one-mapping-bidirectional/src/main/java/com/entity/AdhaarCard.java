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
    @OneToOne(mappedBy = "adhaarCard")
    private Person person;

    @Override
    public String toString() {
       return toString(false);
    }

    public String toString(boolean includePersonDetails) {
        return "AdhaarCard{" +
                "id=" + id +
                ", adhaarNo='" + adhaarNo + '\'' +
                (includePersonDetails ? ", person=" + person.toString(false): "") +
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
