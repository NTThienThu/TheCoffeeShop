package com.ioiDigital.TheCoffeeShop.service;

import com.ioiDigital.TheCoffeeShop.dto.request.OrderCreateDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.MessageResponse;
import com.ioiDigital.TheCoffeeShop.dto.response.OrderResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.Order;

import java.util.List;

public interface OrderService {

    OrderResponseDTO getOrderDetailById(Long orderId);

    int getQueuePosition(Long orderId);

    int getEstimatedWaitingTime(Long orderId);


    Order getOrderById(Long id);

    OrderResponseDTO createOrder(OrderCreateDTO orderCreateDTO);

    List<Order> findAllByQueueId(Long id);

    List<Order> findAllByStatusAndQueueId(String status, Long id);

    MessageResponse cancelOrder(Long orderId);

    Object completedOrder(Long orderId);

    OrderResponseDTO serveCustomer(Long orderId);
}
