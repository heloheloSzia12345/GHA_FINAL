package org.example.backend.mapper;

import org.example.backend.dto.CarDTO;
import org.example.backend.entity.Car;

public class CarMapper {

    public static CarDTO toDto(Car car) {
        return new CarDTO(
                car.getCarId(),
                car.getLicensePlate(),
                car.isRentable(),
                car.getBrand(),
                car.getCarType(),
                car.getColor()
        );
    }

    public static Car toEntity(CarDTO dto) {
        Car car = new Car();
        car.setCarId(dto.getCarId());
        car.setLicensePlate(dto.getLicensePlate());
        car.setRentable(dto.isRentable());
        car.setBrand(dto.getBrand());
        car.setCarType(dto.getCarType());
        car.setColor(dto.getColor());
        return car;
    }

    public void updateEntity(CarDTO dto, Car entity) {
        entity.setCarId(dto.getCarId());
        entity.setLicensePlate(dto.getLicensePlate());
        entity.setRentable(dto.isRentable());
        entity.setBrand(dto.getBrand());
        entity.setCarType(dto.getCarType());
        entity.setColor(dto.getColor());
    }
}