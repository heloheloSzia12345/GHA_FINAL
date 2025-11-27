package org.example.backend.controller;


import org.example.backend.dto.CarDTO;
import org.example.backend.service.CarRentalSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/car")
@CrossOrigin("*")
public class CarController {
    @Autowired
    private CarRentalSystemService carRentalSystemService;
    @GetMapping("/rentable")
    public ResponseEntity<List<CarDTO>> getRentableCars() {
        List<CarDTO> rentableCars=carRentalSystemService.rentableCars();
        return ResponseEntity.ok(rentableCars);
    }

}
