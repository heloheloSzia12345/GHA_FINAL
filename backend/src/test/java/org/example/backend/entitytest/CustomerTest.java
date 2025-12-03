package org.example.backend.entitytest;

import org.example.backend.entity.Customer;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
    @Test
    public void constructorNoArgumentsTest() {
        // Arrange+Act
        Customer customer = new Customer();
        // Assert
        assertNotNull(customer);
        assertNull(customer.getCustomerId());
    }

    @Test
    public void constructorWithAllArgumentsTest() {
        // Arrange+Act
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        Customer customer = new Customer("PU376256", "Kovács János", birthDate);
        // Assert
        assertEquals(customer.getLicenseNum(), "PU376256");
        assertEquals(customer.getName(), "Kovács János");
        assertEquals(customer.getDateOfBirth(), birthDate);
    }
}
