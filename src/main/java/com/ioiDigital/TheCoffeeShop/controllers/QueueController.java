package com.ioiDigital.TheCoffeeShop.controllers;

import com.ioiDigital.TheCoffeeShop.dto.request.ListMenuItemCreateDTO;
import com.ioiDigital.TheCoffeeShop.entity.Order;
import com.ioiDigital.TheCoffeeShop.service.OrderService;
import com.ioiDigital.TheCoffeeShop.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/queue")
public class QueueController {
    @Autowired
    private QueueService queueService;

    @PutMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity updateQueue(@RequestParam int maxSize) {
        return new ResponseEntity<>(this.queueService.updateSizeOfQueue(maxSize), HttpStatus.CREATED);
    }

    @Autowired
    private OrderService orderService;



}
