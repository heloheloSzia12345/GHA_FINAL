package org.example.backend.mappertest;

import org.example.backend.dto.CarDTO;
import org.example.backend.dto.CustomerDTO;
import org.example.backend.dto.RentalDTO;
import org.example.backend.entity.Car;
import org.example.backend.entity.Customer;
import org.example.backend.entity.Rental;
import org.example.backend.mapper.RentalMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RentalMapperTest {

    private Car testCar;
    private Customer testCustomer;
    private CarDTO testCarDTO;
    private CustomerDTO testCustomerDTO;

    @BeforeEach
    void setup() {
        testCar = new Car("ABC-123", true, "Audi", "A4", "Black");
        testCar.setCarId(1L);
        testCustomer = new Customer("TEST", "Teszt Elek", LocalDate.of(1990, 5, 15));
        testCustomer.setCustomerId(1L);
        testCarDTO = new CarDTO(1L, "ABC-123", true, "Audi", "A4", "Black");
        testCustomerDTO = new CustomerDTO(1L, "TEST", "Teszt Elek", LocalDate.of(1990, 5, 15));
    }

    @Test
    public void dtoToEntityTest() {
        // Arrange
        RentalDTO dto = new RentalDTO(
                1L,
                testCarDTO,
                testCustomerDTO,
                LocalDate.of(2025, 11, 20),
                LocalDate.of(2025, 11, 25),
                LocalDate.of(2025, 11, 27),
                50000
        );
        // Act
        Rental rental = RentalMapper.toEntity(dto);
        // Assert
        assertNotNull(rental);
        assertEquals(rental.getRentalId(), 1L);
        assertNotNull(rental.getCar());
        assertNotNull(rental.getCustomer());
        assertEquals(rental.getPickUpDate(), LocalDate.of(2025, 11, 20));
        assertEquals(rental.getDropOffDate(), LocalDate.of(2025, 11, 25));
        assertEquals(rental.getDeadline(), LocalDate.of(2025, 11, 27));
        assertEquals(rental.getPreis(), 50000);
    }

    @Test
    public void entityToDtoTest() {
        // Arrange
        Rental rental = new Rental(
                testCar,
                testCustomer,
                LocalDate.of(2025, 11, 20),
                LocalDate.of(2025, 11, 27),
                LocalDate.of(2025, 11, 25)
        );
        rental.setRentalId(1L);
        rental.setPreis(50000);
        // Act
        RentalDTO rentalDTO = RentalMapper.toDto(rental);
        // Assert
        assertNotNull(rentalDTO);
        assertEquals(rentalDTO.getRentalId(), 1L);
        assertNotNull(rentalDTO.getCar());
        assertNotNull(rentalDTO.getCustomer());
        assertEquals(rentalDTO.getPickUpDate(), LocalDate.of(2025, 11, 20));
        assertEquals(rentalDTO.getDropOffDate(), LocalDate.of(2025, 11, 25));
        assertEquals(rentalDTO.getDeadline(), LocalDate.of(2025, 11, 27));
        assertEquals(rentalDTO.getPreis(), 50000);
    }

    @Test
    public void updateTest() {
        // Arrange
        Rental rental = new Rental(
                testCar,
                testCustomer,
                LocalDate.of(2025, 11, 20),
                LocalDate.of(2025, 11, 27),
                null
        );
        rental.setRentalId(5L);
        rental.setPreis(0);
        RentalDTO updatedDto = new RentalDTO(
                5L,
                testCarDTO,
                testCustomerDTO,
                LocalDate.of(2025, 11, 20),
                LocalDate.of(2025, 11, 28),
                LocalDate.of(2025, 11, 27),
                80000
        );
        // Act
        RentalMapper mapper = new RentalMapper();
        mapper.updateEntity(updatedDto, rental);
        // Assert
        assertNotNull(rental);
        assertEquals(rental.getRentalId(), 5L);
        assertNotNull(rental.getCar());
        assertNotNull(rental.getCustomer());
        assertEquals(rental.getPickUpDate(), LocalDate.of(2025, 11, 20));
        assertEquals(rental.getDropOffDate(), LocalDate.of(2025, 11, 28));
        assertEquals(rental.getDeadline(), LocalDate.of(2025, 11, 27));
        assertEquals(rental.getPreis(), 80000);
    }
}
