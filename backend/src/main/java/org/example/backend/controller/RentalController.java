package org.example.backend.controller;

import org.example.backend.dto.RentalDTO;
import org.example.backend.requestdto.DropOffRequest;
import org.example.backend.requestdto.RentalRequest;
import org.example.backend.service.CarRentalSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rental")
@CrossOrigin("*")
public class RentalController {
    @Autowired
    private CarRentalSystemService carRentalSystemService;

    @PostMapping("/renting")
    public ResponseEntity<RentalDTO> newCarRenting(@RequestBody RentalRequest rentalRequest) {
        RentalDTO rentalDTO=  carRentalSystemService.newCarRenting(
                rentalRequest.getLicensePlate(),
                rentalRequest.getLicenseNum(),
                rentalRequest.getName(),
                rentalRequest.getDateOfBirth(),
                rentalRequest.getPickUpDate(),
                rentalRequest.getDeadline()
        );
        return ResponseEntity.ok(rentalDTO);
    }

    @PostMapping("/drop")
    public ResponseEntity<RentalDTO> dropOff(@RequestBody DropOffRequest dropOffRequest) {
        RentalDTO rentalDTO = carRentalSystemService.dropOffCar(
            dropOffRequest.getLicenseNum(),
            dropOffRequest.getName(),
            dropOffRequest.getDropOffDate()
        );
        return ResponseEntity.ok(rentalDTO);
    }
}
