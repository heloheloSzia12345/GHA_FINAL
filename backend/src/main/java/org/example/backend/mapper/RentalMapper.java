package org.example.backend.mapper;

import org.example.backend.dto.RentalDTO;
import org.example.backend.entity.Rental;

public class RentalMapper {
    public static RentalDTO toDto(Rental rental) {
        return new RentalDTO(
                rental.getRentalId(),
                CarMapper.toDto(rental.getCar()),
                CustomerMapper.toDto(rental.getCustomer()),
                rental.getPickUpDate(),
                rental.getDropOffDate(),
                rental.getDeadline(),
                rental.getPreis()
        );
    }

    public static Rental toEntity(RentalDTO dto) {
        Rental rental = new Rental();
        rental.setRentalId(dto.getRentalId());
        rental.setCar(CarMapper.toEntity(dto.getCar()));
        rental.setCustomer(CustomerMapper.toEntity(dto.getCustomer()));
        rental.setPickUpDate(dto.getPickUpDate());
        rental.setDropOffDate(dto.getDropOffDate());
        rental.setDeadline(dto.getDeadline());
        rental.setPreis(dto.getPreis());
        return rental;
    }
    public void updateEntity(RentalDTO dto, Rental rental) {
        rental.setRentalId(dto.getRentalId());
        rental.setCar(CarMapper.toEntity(dto.getCar()));
        rental.setCustomer(CustomerMapper.toEntity(dto.getCustomer()));
        rental.setPickUpDate(dto.getPickUpDate());
        rental.setDropOffDate(dto.getDropOffDate());
        rental.setDeadline(dto.getDeadline());
        rental.setPreis(dto.getPreis());
    }
}