package com.ioiDigital.TheCoffeeShop.dto.response;

import com.ioiDigital.TheCoffeeShop.entity.Customer;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDTO {

    private long id;

    private String customerName;

    private List<OrderItemResponseDTO> orderItemResponseDTOS;


    private LocalDateTime orderDate;


    private LocalDateTime processedDate;


    private double totalAmount;


    private String note;

    private String status;

    private int queuePosition;

    private int estimatedWaitingTime;

}
