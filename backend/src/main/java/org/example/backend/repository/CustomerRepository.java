package org.example.backend.repository;

import org.example.backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Springnek jelezzük, hogy ez egy repository interface
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> { //Örököljük, hogy a CRUD függvényeket implementálja a Spring
    List<Customer> findByLicenseNum(String licenseNum);
}
