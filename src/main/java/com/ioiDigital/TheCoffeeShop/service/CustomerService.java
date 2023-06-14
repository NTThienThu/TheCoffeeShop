package com.ioiDigital.TheCoffeeShop.service;


import com.ioiDigital.TheCoffeeShop.dto.request.CustomerRegisterDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.CustomerResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.Customer;

public interface CustomerService {
    CustomerResponseDTO create (CustomerRegisterDTO customerRegisterDTO);

    CustomerResponseDTO update (CustomerRegisterDTO customerRegisterDTO);

    Customer getCurrentLogInCustomer();

    // Get a customer by id from the database
    Customer getCustomerById(Long id);

    // Save a customer to the database
    void saveCustomer(Customer customer);
}
