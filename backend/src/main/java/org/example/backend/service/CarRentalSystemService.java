package org.example.backend.service;

import jakarta.transaction.Transactional;
import org.example.backend.dto.CarDTO;
import org.example.backend.dto.RentalDTO;
import org.example.backend.entity.Car;
import org.example.backend.entity.Customer;
import org.example.backend.entity.Rental;
import org.example.backend.mapper.CarMapper;
import org.example.backend.mapper.RentalMapper;
import org.example.backend.repository.CarRepository;
import org.example.backend.repository.CustomerRepository;
import org.example.backend.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CarRentalSystemService {
    @Autowired private CarRepository carRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private RentalRepository rentalRepository;

    @Transactional
    public RentalDTO newCarRenting(String licensePlate, String licenseNum, String name, LocalDate dateOfBirth, LocalDate pickUpDate, LocalDate deadline){
        Car car = carRepository.findByLicensePlate(licensePlate);
        if (car == null) {
            throw new RuntimeException("Car not found with license plate: " + licensePlate);
        }
        if (!car.isRentable()) {
            throw new RuntimeException("Car is not available for renting!");
        }
        LocalDate minBirthDate = LocalDate.now().minusYears(18);
        LocalDate today = LocalDate.now();
        if(dateOfBirth.isAfter(minBirthDate)){
            throw new RuntimeException("You must be 18 years old");
        }
        if(!pickUpDate.isBefore(deadline)){
            throw new RuntimeException("Pick up date must be before deadline");
        }
        if(pickUpDate.isBefore(today)){
            throw new RuntimeException("Pick up date cannot be in the past");
        }
        List<Customer> customers = customerRepository.findByLicenseNum(licenseNum);
        Customer customer;
        if(customers.isEmpty()){
            customer = new Customer(licenseNum, name, dateOfBirth);
            customerRepository.save(customer);
        }else{
            customer = customers.get(0);
        }
        car.setRentable(false);
        Rental rental = rentalRepository.save(new Rental(car,customer,pickUpDate,deadline, null));
        return RentalMapper.toDto(rental);
    }

    public List<CarDTO> rentableCars(){
        return carRepository.findByRentableTrue().stream().map(CarMapper::toDto).toList();
    }

    @Transactional
    public RentalDTO dropOffCar(String licenseNum,String name,LocalDate dropOffDate){
        Rental rental=rentalRepository.findByCustomer_LicenseNumAndDropOffDateIsNull(licenseNum);
        if (rental == null) {
            throw new RuntimeException("No active rental found for customer: " + licenseNum);
        }
        if (dropOffDate.isBefore(rental.getPickUpDate())) {
            throw new RuntimeException("Drop off date cannot be before pick up date");
        }
        Car car=rental.getCar();
        car.setRentable(true);
        carRepository.save(car);
        rental.setDropOffDate(dropOffDate);
        int preis;
        int preisProTag=10000;
        long penalty= 0;
        long planTage = ChronoUnit.DAYS.between(rental.getPickUpDate(), rental.getDeadline());
        long aktualTage = ChronoUnit.DAYS.between(rental.getPickUpDate(), rental.getDropOffDate());
        if (planTage<aktualTage){
            penalty=15000*(aktualTage-planTage);
        }
        preis= Math.toIntExact(preisProTag * aktualTage+penalty);
        rental.setPreis(preis);
        return RentalMapper.toDto(rentalRepository.save(rental));
    }


}
