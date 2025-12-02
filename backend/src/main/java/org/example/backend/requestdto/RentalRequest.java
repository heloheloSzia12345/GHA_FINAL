package org.example.backend.requestdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequest {
    private String licensePlate;
    private String licenseNum;
    private String name;
    private LocalDate dateOfBirth;
    private LocalDate pickUpDate;
    private LocalDate deadline;
}
