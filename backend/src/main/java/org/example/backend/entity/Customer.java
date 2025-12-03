package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

// Lombok annotációk
@Getter
@Setter
@NoArgsConstructor
@Entity // JPA-nak, hogy ez egy tábla
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto increment, ez a primary key
    private Long customerId;
    @Column(unique = true, nullable = false) // License Num unique és not null lesz
    private String licenseNum;
    private String name;
    private LocalDate dateOfBirth;

    public Customer(String licenseNum, String name, LocalDate dateOfBirth) {
        this.licenseNum = licenseNum;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }
}