package com.ioiDigital.TheCoffeeShop.controllers;

import com.ioiDigital.TheCoffeeShop.dto.request.CustomerRegisterDTO;
import com.ioiDigital.TheCoffeeShop.entity.Customer;
import com.ioiDigital.TheCoffeeShop.entity.Order;
import com.ioiDigital.TheCoffeeShop.repository.CustomerRepository;
import com.ioiDigital.TheCoffeeShop.repository.OrderRepository;
import com.ioiDigital.TheCoffeeShop.service.CustomerService;
import com.ioiDigital.TheCoffeeShop.service.OrderService;
import com.ioiDigital.TheCoffeeShop.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CustomerRegisterDTO customerRegisterDTO) {
        return new ResponseEntity<>(customerService.create(customerRegisterDTO), HttpStatus.CREATED);
    }

    @PutMapping("")
    @PreAuthorize("hasAuthority('Customer')")
    public ResponseEntity<?> update(@RequestBody CustomerRegisterDTO customerRegisterDTO) {
        return new ResponseEntity<>(customerService.update(customerRegisterDTO), HttpStatus.OK);
    }



}
