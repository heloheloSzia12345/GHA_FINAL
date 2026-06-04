package org.example.backend.repository;

import org.example.backend.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Springnek jelezzük, hogy ez egy repository interface
@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> { //Örököljük, hogy a CRUD függvényeket implementálja a Spring
    Optional<Rental> findByCustomer_CustomerIdAndDropOffDateIsNull(Long customerId);

    Rental findByCustomer_LicenseNumAndDropOffDateIsNull(String licenseNum);
}
