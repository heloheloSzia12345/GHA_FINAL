package org.example.backend.entitytest;

import org.example.backend.entity.Car;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {
    @Test
    public void constructorAllArgumentsTest() {
        // Arrange+Act
        Car car = new Car("ABC-123", true, "Audi", "A4", "Black");
        // Assert
        assertEquals(car.getLicensePlate(), "ABC-123");
        assertTrue(car.isRentable());
        assertEquals("Audi", car.getBrand());
        assertEquals("A4", car.getCarType());
        assertEquals("Black", car.getColor());
    }

    @Test
    public void constructorNoArgumentsTest() {
        // Arrange+Act
        Car car = new Car();
        // Assert
        assertNotNull(car);
        assertNull(car.getCarId());
    }
}
