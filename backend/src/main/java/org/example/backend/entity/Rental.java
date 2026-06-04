package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

// Lombok annotációk
@Getter
@Setter
@NoArgsConstructor
@Entity // JPA-nak, hogy ez egy tábla
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto increment, ez a primary key
    private Long rentalId;
    @ManyToOne // N:1 kapcsolatot hozok létre
    @JoinColumn(name = "carId") // carId oszlopnak lesz ez az idegen kulcsa
    private Car car;
    @ManyToOne // N:1 kapcsolatot hozok létre
    @JoinColumn(name = "customerId") // customerId oszlopnak lesz ez az idegen kulcsa
    private Customer customer;
    private LocalDate pickUpDate;
    private LocalDate dropOffDate;
    private LocalDate deadline;
    private int preis;

    public Rental(Car car, Customer customer, LocalDate pickUpDate, LocalDate deadline, LocalDate dropOffDate) {
        this.car = car;
        this.customer = customer;
        this.pickUpDate = pickUpDate;
        this.deadline = deadline;
        this.dropOffDate = dropOffDate;
    }
}