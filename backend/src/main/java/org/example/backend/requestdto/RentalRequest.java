package org.example.backend.requestdto;

import java.time.LocalDate;
import lombok.*;

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
