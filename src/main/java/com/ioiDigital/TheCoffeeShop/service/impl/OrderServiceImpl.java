package com.ioiDigital.TheCoffeeShop.service.impl;

import com.ioiDigital.TheCoffeeShop.dto.request.OrderCreateDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.OrderItemResponseDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.OrderResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.Customer;
import com.ioiDigital.TheCoffeeShop.entity.MenuItem;
import com.ioiDigital.TheCoffeeShop.entity.Order;
import com.ioiDigital.TheCoffeeShop.entity.OrderItem;
import com.ioiDigital.TheCoffeeShop.mapper.OrderMapper;
import com.ioiDigital.TheCoffeeShop.repository.CustomerRepository;
import com.ioiDigital.TheCoffeeShop.repository.OrderRepository;
import com.ioiDigital.TheCoffeeShop.repository.QueueRepository;
import com.ioiDigital.TheCoffeeShop.service.CustomerService;
import com.ioiDigital.TheCoffeeShop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private QueueRepository queueRepository;

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Order getOrderByIdAndCustomerId(Long orderId, Long customerId) {
        return orderRepository.findByIdAndCustomerId(orderId, customerId).orElse(null);
    }

    @Override
    public int getQueuePosition(Long orderId) {
        // Get the order by id
        Order order = orderRepository.findById(orderId).orElse(null);
        // Check if the order exists and is pending
        if (order != null && order.getStatus().equals("pending")) {
            // Get the list of pending orders before the given order
            List<Order> ordersBefore = orderRepository.findByStatusAndCreatedTimeBefore("pending", order.getOrderDate());
            // Return the size of the list plus one as the queue position
            return ordersBefore.size() + 1;
        } else {
            // Return -1 if the order does not exist or is not pending
            return -1;
        }
    }

    @Override
    public int getEstimatedWaitingTime(Long orderId) {
        // Get the queue position of the order
        int queuePosition = getQueuePosition(orderId);
        // Check if the queue position is valid
        if (queuePosition > 0) {
            // Get the average processing time of completed orders from the database
            Double averageProcessingTime = orderRepository.getAverageProcessingTime();
            // Check if the average processing time is not null
            if (averageProcessingTime != null) {
                // Return the product of queue position and average processing time as the estimated waiting time in seconds
                return (int) Math.round(queuePosition * averageProcessingTime);
            } else {
                // Return -1 if the average processing time is null
                return -1;
            }
        } else {
            // Return -1 if the queue position is invalid
            return -1;
        }
    }

    @Override
    public Order getOrderByIdAndQueueId(Long orderId, Long queueId) {
        return orderRepository.findByIdAndQueueId(orderId, queueId).orElse(null);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }


    @Override
    public OrderResponseDTO createOrder(OrderCreateDTO orderCreateDTO, int shopId) {
        // Get the customer by id
        Customer customer = customerService.getCurrentLogInCustomer();
        Order order = orderMapper.toEntity(orderCreateDTO);

        order.setQueue(queueRepository.findByShop_Id(shopId));

        order.setCustomer(customer);
        order.setStatus("pending");
        order.setNote(orderCreateDTO.getNote());
        order.setOrderDate(LocalDateTime.now());


        double totalPrice = 0;

        for (OrderItem orderItem : orderCreateDTO.getOrderItems()) {
            MenuItem menuItem = orderItem.getMenuItem();
            int quantity = orderItem.getQuantity();
            totalPrice += menuItem.getPrice() * quantity;
        }

        order.setTotalAmount(totalPrice);

        orderRepository.save(order);
        return null;
}

}