package com.ioiDigital.TheCoffeeShop.service;

import com.ioiDigital.TheCoffeeShop.dto.request.OrderCreateDTO;
import com.ioiDigital.TheCoffeeShop.entity.Order;

public interface OrderService {
    Order getOrderByIdAndQueueId(Long orderId, Long queueId);

    void saveOrder(Order order);

    Order getOrderByIdAndCustomerId(Long orderId, Long customerId);

    // Get the queue position of an order based on the creation time and status
    int getQueuePosition(Long orderId);

    // Get the estimated waiting time of an order based on the queue position and average processing time
    int getEstimatedWaitingTime(Long orderId);

    // Get an order by id
    Order getOrderById(Long id);

    Object createOrder(OrderCreateDTO orderCreateDTO, int shopId);
}
