package org.example.backend.mappertest;

import org.example.backend.dto.CustomerDTO;
import org.example.backend.entity.Customer;
import org.example.backend.mapper.CustomerMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerMapperTest {
    @Test
    public void entityToDtoTest() {
        // Arrange
        Customer customer = new Customer("PU376256", "Kovács János", LocalDate.of(1990, 5, 15));
        customer.setCustomerId(1L);
        // Act
        CustomerDTO customerDTO = CustomerMapper.toDto(customer);
        // Assert
        assertNotNull(customerDTO);
        assertEquals(customerDTO.getCustomerId(), 1L);
        assertEquals(customerDTO.getLicenseNum(), "PU376256");
        assertEquals(customerDTO.getName(), "Kovács János");
        assertEquals(customerDTO.getDateOfBirth(), LocalDate.of(1990, 5, 15));
    }

    @Test
    public void dtoToEntiyTest() {
        // Arrange
        CustomerDTO customerDTO = new CustomerDTO(1L, "PU376256", "Kovács János", LocalDate.of(1990, 5, 15));
        // Act
        Customer customer = CustomerMapper.toEntity(customerDTO);
        // Assert
        assertNotNull(customer);
        assertEquals(customer.getCustomerId(), 1L);
        assertEquals(customer.getLicenseNum(), "PU376256");
        assertEquals(customer.getName(), "Kovács János");
        assertEquals(customer.getDateOfBirth(), LocalDate.of(1990, 5, 15));
    }

    @Test
    public void updateTest() {
        // Arrange
        Customer customer = new Customer("PU376256", "Kovács János", LocalDate.of(1990, 5, 15));
        customer.setCustomerId(1L);
        CustomerDTO updatedDto = new CustomerDTO(10L, "NEW456", "Új Név", LocalDate.of(1995, 5, 5));
        CustomerMapper mapper = new CustomerMapper();
        // Act
        mapper.updateEntity(updatedDto, customer);
        // Assert
        assertNotNull(customer);
        assertEquals(customer.getCustomerId(), 10L);
        assertEquals(customer.getLicenseNum(), "NEW456");
        assertEquals(customer.getName(), "Új Név");
        assertEquals(customer.getDateOfBirth(), LocalDate.of(1995, 5, 5));
    }

}
