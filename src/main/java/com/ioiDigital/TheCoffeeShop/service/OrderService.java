package com.ioiDigital.TheCoffeeShop.service;

import com.ioiDigital.TheCoffeeShop.dto.request.OrderCreateDTO;
import com.ioiDigital.TheCoffeeShop.entity.Order;

public interface OrderService {
    // Get an order by id and queue id from the database
    Order getOrderByIdAndQueueId(Long orderId, Long queueId);

    // Save an order to the database
    void saveOrder(Order order);

    // Get an order by id and customer id from the database
    Order getOrderByIdAndCustomerId(Long orderId, Long customerId);

    // Get the queue position of an order based on the creation time and status
    int getQueuePosition(Long orderId);

    // Get the estimated waiting time of an order based on the queue position and average processing time
    int getEstimatedWaitingTime(Long orderId);

    // Get an order by id
    Order getOrderById(Long id);

    Object createOrder(Order order);

    Object createOrder(OrderCreateDTO orderCreateDTO, int shopId);
}
