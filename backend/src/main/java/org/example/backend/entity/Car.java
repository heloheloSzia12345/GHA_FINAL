package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;
    @Column(unique = true, nullable = false)
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