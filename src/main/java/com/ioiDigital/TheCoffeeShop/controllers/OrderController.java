package com.ioiDigital.TheCoffeeShop.controllers;

import com.ioiDigital.TheCoffeeShop.dto.request.OrderCreateDTO;
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

    @PostMapping()
    public ResponseEntity<?> createOrder(@RequestBody OrderCreateDTO orderCreateDTO, @PathVariable int shopId) {
        try {

            return new ResponseEntity<>(orderService.createOrder(orderCreateDTO, shopId), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get the queue position, estimated waiting time and status of an order
//    @GetMapping("/{orderId}/customer/{customerId}")
//    public ResponseEntity<Order> getOrderDetails(@PathVariable Long orderId, @PathVariable Long customerId) {
//        try {
//            // Get the order by id and customer id
//            Order order = orderService.getOrderByIdAndCustomerId(orderId, customerId);
//            // Check if the order exists
//            if (order == null) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//            // Get the queue position and estimated waiting time for the order
//            int queuePosition = orderService.getQueuePosition(orderId);
//            int estimatedWaitingTime = orderService.getEstimatedWaitingTime(orderId);
//            // Set the queue position and estimated waiting time for the order
//            order.setQueuePosition(queuePosition);
//            order.setEstimatedWaitingTime(estimatedWaitingTime);
//            return new ResponseEntity<>(order, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @Autowired
//    private QueueService queueService;
//
//    // Remove a customer from the queue and cancel their order
//    @DeleteMapping("/{orderId}/customer/{customerId}/remove")
//    public ResponseEntity<Order> cancelOrder(@PathVariable Long customerId, @PathVariable Long orderId) {
//        try {
//            // Get the order by id and customer id
//            Order order = orderService.getOrderByIdAndCustomerId(orderId, customerId);
//            // Check if the order exists and is pending
//            if (order != null && order.getStatus().equals("pending")) {
//                // Remove the order from the queue
//                queueService.removeOrderFromQueue(order);
//                // Set the status for the order
//                order.setStatus("cancelled");
//                // Save the order to the database
//                orderService.saveOrder(order);
//                // Update the queue position and estimated waiting time of other orders in the queue
//                queueService.updateQueueDetails();
//                return new ResponseEntity<>(order, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // Update the order status to complete and increase the customer's service count
//    @PutMapping("/{orderId}/complete")
//    public ResponseEntity<Order> completeOrder(@PathVariable Long orderId) {
//        try {
//            // Get the order by id
//            Order order = orderService.getOrderById(orderId);
//            // Check if the order exists and is processing
//            if (order != null && order.getStatus().equals("processing")) {
//                // Set the status for the order
//                order.setStatus("completed");
//                // Save the order to the database
//                orderService.saveOrder(order);
//                // Get the customer who placed the order
//                Customer customer = order.getCustomer();
//                // Check if the customer exists
//                if (customer != null) {
//                    // Increase the customer's service count by one
//                    customer.setServiceCount(customer.getServiceCount() + 1);
//                    // Save the customer to the database
//                    customerService.saveCustomer(customer);
//                }
//                return new ResponseEntity<>(order, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
