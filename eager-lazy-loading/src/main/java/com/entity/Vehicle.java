package com.entity;

import com.enums.VehicleType;
import jakarta.persistence.*;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false, unique = true)
    private String VehicleNumber;
    @OneToOne(mappedBy = "vehicle")
    private Person owner;

    @Override
    public String toString() {
       return toString(false);
    }

    public String toString(boolean includeOwner) {
        return "Vehicle{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", model='" + model + '\'' +
                ", vehicleNumber='" + VehicleNumber + '\'' +
                (includeOwner ? (", owner=" + owner) :  "")+
                '}';
    }

    public int getId() {
        return id;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getVehicleNumber() {
        return VehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        VehicleNumber = vehicleNumber;
    }
}
