package org.example.backend.servicetest;

import org.example.backend.dto.CarDTO;
import org.example.backend.entity.Car;
import org.example.backend.repository.CarRepository;
import org.example.backend.service.CarRentalSystemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Mocking-hoz
public class CarRentalSystemServiceTest {
    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarRentalSystemService service;

    private Car testCar;

    @Test
    void rentableCarsTest() {
        // Arrange
        testCar = new Car("ABC-123", true, "Audi", "A4", "Black");
        testCar.setCarId(1L);
        when(carRepository.findByRentableTrue()).thenReturn(List.of(testCar));
        // Act
        List<CarDTO> result = service.rentableCars();
        // Assert
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getCarId(), 1L);
        assertEquals(result.get(0).getLicensePlate(), "ABC-123");
    }
}
