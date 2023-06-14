package com.ioiDigital.TheCoffeeShop.controllers;

import com.ioiDigital.TheCoffeeShop.dto.request.ListMenuItemCreateDTO;
import com.ioiDigital.TheCoffeeShop.entity.Order;
import com.ioiDigital.TheCoffeeShop.service.OrderService;
import com.ioiDigital.TheCoffeeShop.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/queue")
public class QueueController {
    @Autowired
    private QueueService queueService;

    @PutMapping
    public ResponseEntity updateQueue(@RequestParam int maxSize) {
        return new ResponseEntity<>(this.queueService.updateSizeOfQueue(maxSize), HttpStatus.CREATED);
    }

    @Autowired
    private OrderService orderService;

    // Remove a customer from the queue and serve them
    @PutMapping("/{queueId}/orders/{orderId}")
    public ResponseEntity<Order> serveCustomer(@PathVariable Long queueId, @PathVariable Long orderId) {
        try {
            // Get the order by id and queue id
            Order order = orderService.getOrderByIdAndQueueId(orderId, queueId);
            // Check if the order exists and is pending
            if (order != null && order.getStatus().equals("pending")) {
                // Remove the order from the queue
                queueService.removeOrderFromQueue(order);
                // Set the status and processed time for the order
                order.setStatus("processing");
                order.setProcessedDate(LocalDateTime.now());
                // Save the order to the database
                orderService.saveOrder(order);
                return new ResponseEntity<>(order, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
