package org.example.backend.dto;

import lombok.*;

import java.time.LocalDate;

// Lombok annotációk
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO { // Customer Entity leképzése Data Transfer Object-é
    private Long customerId;
    private String licenseNum;
    private String name;
    private LocalDate dateOfBirth;
}