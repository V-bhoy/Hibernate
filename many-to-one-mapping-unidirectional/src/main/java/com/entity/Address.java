package com.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String city;
    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false, updatable = false)
    private Person person;

    public int getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
      return toString(false);
    }

    public String toString(boolean showPersonDetails){
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                (showPersonDetails ? ", person=" + person: "") +
                '}';
    }
}
