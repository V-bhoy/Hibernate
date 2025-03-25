package com.entity;

import com.enums.Gender;
import jakarta.persistence.*;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int age;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;
    @Column(nullable = false)
    private String address;
    @Column(length = 10, unique = true)
    private String phone;
    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "adhaar_id", referencedColumnName = "id", unique = true, nullable = false)
    private AdhaarCard adhaarCard;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", adhaarCard=" + adhaarCard +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AdhaarCard getAdhaarCard() {
        return adhaarCard;
    }

    public void setAdhaarCard(AdhaarCard adhaarCard) {
        this.adhaarCard = adhaarCard;
    }
}
