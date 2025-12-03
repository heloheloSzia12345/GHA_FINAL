package org.example.backend.dto;

import lombok.*;

import java.time.LocalDate;

// Lombok annotációk
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalDTO { // Rental Entity leképzése Data Transfer Object-é
    private Long rentalId;
    private CarDTO car;
    private CustomerDTO customer;
    private LocalDate pickUpDate;
    private LocalDate dropOffDate;
    private LocalDate deadline;
    private int preis;
}