package org.example.backend.servicetest;

import org.example.backend.dto.CarDTO;
import org.example.backend.dto.RentalDTO;
import org.example.backend.entity.Car;
import org.example.backend.entity.Customer;
import org.example.backend.entity.Rental;
import org.example.backend.repository.CarRepository;
import org.example.backend.repository.CustomerRepository;
import org.example.backend.repository.RentalRepository;
import org.example.backend.service.CarRentalSystemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Mocking-hoz
public class CarRentalSystemServiceTest {
    @Mock
    private CarRepository carRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RentalRepository rentalRepository;

    @InjectMocks // Tesztelhető objektum
    private CarRentalSystemService service;

    private Car testCar;
    private Customer testCustomer;

    @BeforeEach // Mindegyik előtt megcsinálja
    public void setUp() {
        testCar = new Car("ABC-123", true, "Audi", "A4", "Black");
        testCar.setCarId(1L);
        testCustomer = new Customer("PU376256", "Kovács János", LocalDate.of(1990, 5, 15));
        testCustomer.setCustomerId(1L);
    }

    @Test
    public void rentableCarsTest() {
        // Arrange
        when(carRepository.findByRentableTrue()).thenReturn(List.of(testCar));
        // Act
        List<CarDTO> result = service.rentableCars();
        // Assert
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getCarId(), 1L);
        assertEquals(result.get(0).getLicensePlate(), "ABC-123");
    }

    @Test
    public void newCarRentingTest() {
        // Assert
        when(customerRepository.findByLicenseNum("PU376256")).thenReturn(List.of(testCustomer));
        when(carRepository.findByLicensePlate("ABC-123")).thenReturn(testCar);
        Rental save = new Rental(testCar, testCustomer, LocalDate.now(), LocalDate.now().plusDays(7), null);
        save.setRentalId(1L);
        when(rentalRepository.save(any(Rental.class))).thenReturn(save);
        // Act
        RentalDTO result = service.newCarRenting("ABC-123", "PU376256", "Kovács János", LocalDate.of(1990, 5, 15), LocalDate.now(), LocalDate.now().plusDays(7));
        // Assert
        assertNotNull(result);
        assertEquals(result.getRentalId(), 1L);
        assertFalse(result.getCar().isRentable());
    }

    @Test
    public void carNotFound() {
        // Arrange
        when(carRepository.findByLicensePlate("ABC-123")).thenReturn(null);
        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.newCarRenting("ABC-123", "PU376256", "Kovács János",
                    LocalDate.now().minusYears(30), LocalDate.now().plusDays(1), LocalDate.now().plusDays(8));
        });
        // Assert
        assertEquals("Car not found with license plate: " + "ABC-123", exception.getMessage());
    }

    @Test
    public void carNotRentable() {
        // Arrange
        testCar.setRentable(false);
        when(carRepository.findByLicensePlate("ABC-123")).thenReturn(testCar);
        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.newCarRenting("ABC-123", "PU376256", "Kovács János",
                    LocalDate.now().minusYears(30), LocalDate.now().plusDays(1), LocalDate.now().plusDays(8));
        });
        // Assert
        assertEquals("Car is not available for renting!", exception.getMessage());
    }

    @Test
    public void underAge() {
        // Arrange
        when(carRepository.findByLicensePlate("ABC-123")).thenReturn(testCar);
        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.newCarRenting("ABC-123", "PU376256", "Kovács János",
                    LocalDate.now().minusYears(5), LocalDate.now().plusDays(1), LocalDate.now().plusDays(8));
        });
        // Assert
        assertEquals("You must be 18 years old", exception.getMessage());
    }

    @Test
    public void pickUpBeforeDeadline() {
        // Arrange
        when(carRepository.findByLicensePlate("ABC-123")).thenReturn(testCar);
        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.newCarRenting("ABC-123", "PU376256", "Kovács János",
                    LocalDate.now().minusYears(30), LocalDate.now().plusDays(1), LocalDate.now().plusDays(1));
        });
        // Assert
        assertEquals("Pick up date must be before deadline", exception.getMessage());
    }

    @Test
    public void pickUpIsInThePast() {
        // Arrange
        when(carRepository.findByLicensePlate("ABC-123")).thenReturn(testCar);
        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.newCarRenting("ABC-123", "PU376256", "Kovács János",
                    LocalDate.now().minusYears(30), LocalDate.now().plusDays(-4), LocalDate.now().plusDays(5));
        });
        // Assert
        assertEquals("Pick up date cannot be in the past", exception.getMessage());
    }

    @Test
    void newCustomer() {
        // Arrange
        when(customerRepository.findByLicenseNum("PU376256")).thenReturn(List.of());
        when(carRepository.findByLicensePlate("ABC-123")).thenReturn(testCar);
        Rental save = new Rental(testCar, testCustomer, LocalDate.now(), LocalDate.now().plusDays(7), null);
        when(rentalRepository.save(any(Rental.class))).thenReturn(save);
        // Act
        service.newCarRenting("ABC-123", "PU376256", "Kovács János",
                LocalDate.of(1995, 1, 1),
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(8));
        // Assert
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void dropOffTest() {
        // Arrange
        Rental rental = new Rental(testCar, testCustomer, LocalDate.of(2025, 11, 20), LocalDate.of(2025, 11, 27), LocalDate.of(2025, 11, 30));
        when(rentalRepository.findByCustomer_LicenseNumAndDropOffDateIsNull("ABC-123")).thenReturn(rental);
        when(carRepository.save(any(Car.class))).thenReturn(testCar);
        when(rentalRepository.save(any(Rental.class))).thenReturn(rental);
        // Act
        RentalDTO result = service.dropOffCar("ABC-123", "Kovács János", LocalDate.of(2025, 11, 30));
        // Assert
        assertEquals(result.getPreis(), 145000);
    }

    @Test
    public void noActiveRental() {
        // Arrange
        when(rentalRepository.findByCustomer_LicenseNumAndDropOffDateIsNull("ABC-124")).thenReturn(null);
        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.dropOffCar("ABC-124", "Kovács János", LocalDate.of(2025, 11, 30));
        });
        // Assert
        assertEquals("No active rental found for customer: " + "ABC-124", exception.getMessage());
    }

    @Test
    public void dropOffDateBeforePickUpDate() {
        // Arrange
        Rental rental = new Rental(testCar, testCustomer, LocalDate.of(2025, 11, 20), LocalDate.of(2025, 11, 21), null);
        when(rentalRepository.findByCustomer_LicenseNumAndDropOffDateIsNull("ABC-123")).thenReturn(rental);
        // Act
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.dropOffCar("ABC-123", "Kovács János", LocalDate.of(2025, 11, 19));
        });
        // Assert
        assertEquals("Drop off date cannot be before pick up date", exception.getMessage());
    }
}

