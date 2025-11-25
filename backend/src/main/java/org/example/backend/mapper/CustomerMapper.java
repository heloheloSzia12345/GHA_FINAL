package org.example.backend.mapper;

import org.example.backend.dto.CustomerDTO;
import org.example.backend.entity.Customer;

public class CustomerMapper {
    public static CustomerDTO toDto(Customer customer) {
        return new CustomerDTO(
                customer.getCustomerId(),
                customer.getLicenseNum(),
                customer.getName(),
                customer.getDateOfBirth()
        );
    }
    public static Customer toEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setCustomerId(customerDTO.getCustomerId());
        customer.setLicenseNum(customerDTO.getLicenseNum());
        customer.setName(customerDTO.getName());
        customer.setDateOfBirth(customerDTO.getDateOfBirth());
        return customer;
    }
    public void updateEntity(CustomerDTO customerDTO, Customer entity) {
        entity.setCustomerId(customerDTO.getCustomerId());
        entity.setLicenseNum(customerDTO.getLicenseNum());
        entity.setName(customerDTO.getName());
        entity.setDateOfBirth(customerDTO.getDateOfBirth());
    }
}