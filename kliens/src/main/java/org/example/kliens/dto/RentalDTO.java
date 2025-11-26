package org.example.kliens.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalDTO {
    private Long rentalId;
    private CarDTO car;
    private CustomerDTO customer;
    private LocalDate pickUpDate;
    private LocalDate dropOffDate;
    private LocalDate deadline;
    private int preis;
}