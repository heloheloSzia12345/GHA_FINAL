package org.example.backend.entitytest;

import org.example.backend.entity.Car;
import org.example.backend.entity.Customer;
import org.example.backend.entity.Rental;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RentalTest {
    @Test
    public void constructorNoArgumentsTest() {
        // Arrange+Act
        Rental rental = new Rental();
        // Assert
        assertNotNull(rental);
        assertNull(rental.getRentalId());
    }

    @Test
    public void constructorWithAllArgumentsTest() {
        // Arrange+Act
        LocalDate pickUpDate = LocalDate.of(2025, 11, 20);
        LocalDate deadline = LocalDate.of(2025, 11, 27);
        LocalDate dropOffDate = LocalDate.of(2025, 11, 25);
        Car testCar = new Car("ABC-123", true, "Audi", "A4", "Black");
        testCar.setCarId(1L);
        Customer testCustomer = new Customer("TEST", "Teszt Elek", LocalDate.of(1990, 5, 15));
        testCustomer.setCustomerId(1L);
        Rental rental = new Rental(testCar, testCustomer, pickUpDate, deadline, dropOffDate);
        // Assert
        assertEquals(rental.getCar(), testCar);
        assertEquals(rental.getCustomer(), testCustomer);
        assertEquals(rental.getPickUpDate(), pickUpDate);
        assertEquals(rental.getDeadline(), deadline);
        assertEquals(rental.getDropOffDate(), dropOffDate);
    }
}
