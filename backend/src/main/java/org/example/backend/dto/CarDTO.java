package org.example.backend.dto;

import lombok.*;

// Lombok annotációk
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {  // Car Entity leképzése Data Transfer Object-é
    private Long carId;
    private String licensePlate;
    private boolean rentable;
    private String brand;
    private String carType;
    private String color;
}