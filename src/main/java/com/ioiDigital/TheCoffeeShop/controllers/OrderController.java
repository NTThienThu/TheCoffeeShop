package com.ioiDigital.TheCoffeeShop.controllers;

import com.ioiDigital.TheCoffeeShop.dto.request.OrderCreateDTO;
import com.ioiDigital.TheCoffeeShop.entity.Customer;
import com.ioiDigital.TheCoffeeShop.entity.EStatusOrder;
import com.ioiDigital.TheCoffeeShop.entity.Order;
import com.ioiDigital.TheCoffeeShop.mapper.OrderMapper;
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

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private QueueService queueService;

    @Autowired
    private OrderMapper orderMapper;

    @PostMapping
    @PreAuthorize("hasRole('Customer')")
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateDTO orderCreateDTO) {
        return new ResponseEntity<>(orderService.createOrder(orderCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}/customer/{customerId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long orderId, @PathVariable Long customerId) {
        return new ResponseEntity<>(orderMapper.toDTO(orderService.getOrderByIdAndCustomerId(orderId, customerId)), HttpStatus.OK);
    }


    @DeleteMapping("/{orderId}/customer/{customerId}/remove")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId, @PathVariable Long customerId) {
        return ResponseEntity.ok(this.orderService.cancelOrder(orderId, customerId));

    }

    @PutMapping("/{orderId}/complete")
    @PreAuthorize("hasRole('Employee')")
    public ResponseEntity<?> completeOrder(@PathVariable Long orderId) {
        return new ResponseEntity<>(orderService.completedOrder(orderId), HttpStatus.OK);
    }

}
