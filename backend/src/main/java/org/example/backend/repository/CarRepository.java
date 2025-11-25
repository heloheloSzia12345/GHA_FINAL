package org.example.backend.repository;

import org.example.backend.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car findByLicensePlate(String licensePlate);
    List<Car> findByRentableTrue();
}
