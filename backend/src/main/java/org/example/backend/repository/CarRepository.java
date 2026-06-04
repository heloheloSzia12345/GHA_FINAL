package org.example.backend.repository;

import org.example.backend.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Springnek jelezzük, hogy ez egy repository interface
@Repository // Azért long <> között mert olyan típusú a primary key
public interface CarRepository extends JpaRepository<Car, Long> { //Örököljük, hogy a CRUD függvényeket implementálja a Spring
    Car findByLicensePlate(String licensePlate);

    List<Car> findByRentableTrue();
}
