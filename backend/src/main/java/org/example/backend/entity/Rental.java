package org.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentalId;
    @ManyToOne
    @JoinColumn(name = "carId")
    private Car car;
    @ManyToOne
    @JoinColumn(name = "customerId")
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