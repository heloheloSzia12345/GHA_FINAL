package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

// Lombok annotációk
@Getter
@Setter
@NoArgsConstructor
@Entity // JPA-nak, hogy ez egy tábla
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto increment, ez a primary key
    private Long carId;
    @Column(unique = true, nullable = false) // License Plate unique és not null lesz
    private String licensePlate;
    private boolean rentable;
    private String brand;
    private String carType;
    private String color;

    public Car(String licensePlate, boolean rentable, String brand, String carType, String color) {
        this.licensePlate = licensePlate;
        this.rentable = rentable;
        this.brand = brand;
        this.carType = carType;
        this.color = color;
    }
}