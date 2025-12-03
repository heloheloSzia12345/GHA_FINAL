package org.example.backend.mappertest;

import org.example.backend.dto.CarDTO;
import org.example.backend.entity.Car;
import org.example.backend.mapper.CarMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CarMapperTest {
    @Test
    public void entityToDtoTest() {
        // Arrange
        Car car = new Car("ABC-123", true, "Audi", "A4", "Black");
        car.setCarId(1L);
        // Act
        CarDTO carDTO = CarMapper.toDto(car);
        // Assert
        assertNotNull(carDTO);
        assertEquals(carDTO.getCarId(), 1L);
        assertEquals(carDTO.getLicensePlate(), "ABC-123");
        assertEquals(carDTO.isRentable(), true);
        assertEquals(carDTO.getBrand(), "Audi");
        assertEquals(carDTO.getCarType(), "A4");
        assertEquals(carDTO.getColor(), "Black");
    }

    @Test
    public void dtoToEntityTest() {
        // Arrange
        CarDTO carDTO = new CarDTO(1L, "ABC-123", true, "Audi", "A4", "Black");
        carDTO.setCarId(1L);
        // Act
        Car car = CarMapper.toEntity(carDTO);
        // Assert
        assertNotNull(car);
        assertEquals(car.getCarId(), 1L);
        assertEquals(car.getLicensePlate(), "ABC-123");
        assertEquals(car.isRentable(), true);
        assertEquals(car.getBrand(), "Audi");
        assertEquals(car.getCarType(), "A4");
        assertEquals(car.getColor(), "Black");
    }

    @Test
    public void updateTest() {
        // Arrange
        Car car = new Car("ABC-123", true, "Audi", "A4", "Black");
        car.setCarId(1L);
        CarDTO updatedDto = new CarDTO(5L, "NEW-123", false, "NewBrand", "NewType", "NewColor");
        CarMapper mapper = new CarMapper();
        // Act
        mapper.updateEntity(updatedDto, car);
        // Assert
        assertNotNull(car);
        assertEquals(car.getCarId(), 5L);
        assertEquals(car.getLicensePlate(), "NEW-123");
        assertEquals(car.isRentable(), false);
        assertEquals(car.getBrand(), "NewBrand");
        assertEquals(car.getCarType(), "NewType");
        assertEquals(car.getColor(), "NewColor");
    }
}
