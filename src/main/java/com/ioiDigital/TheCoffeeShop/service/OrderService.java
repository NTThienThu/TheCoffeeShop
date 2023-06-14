package com.ioiDigital.TheCoffeeShop.service;

import com.ioiDigital.TheCoffeeShop.dto.request.OrderCreateDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.MessageResponse;
import com.ioiDigital.TheCoffeeShop.dto.response.OrderResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.Order;

import java.util.List;

public interface OrderService {
    Order getOrderByIdAndQueueId(Long orderId, Long queueId);
    

    Order getOrderByIdAndCustomerId(Long orderId, Long customerId);

    int getQueuePosition(Long orderId);

    int getEstimatedWaitingTime(Long orderId);

    Order getOrderById(Long id);

    OrderResponseDTO createOrder(OrderCreateDTO orderCreateDTO);

    List<Order> findAllByQueueId(Long id);

    MessageResponse cancelOrder(Long orderId, Long customerId);

    Object completedOrder(Long orderId);

    Object serveCustomer(Long orderId, Long queueId);
}
