package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(unique = true, nullable = false)
    private String licenseNum;
    private String name;
    private LocalDate dateOfBirth;

    public Customer(String licenseNum, String name, LocalDate dateOfBirth) {
        this.licenseNum = licenseNum;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }
}