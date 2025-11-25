package org.example.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private Long carId;
    private String licensePlate;
    private boolean rentable;
    private String brand;
    private String carType;
    private String color;
}