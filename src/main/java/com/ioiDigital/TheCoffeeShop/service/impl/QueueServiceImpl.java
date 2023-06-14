package com.ioiDigital.TheCoffeeShop.service.impl;

import com.ioiDigital.TheCoffeeShop.entity.Order;
import com.ioiDigital.TheCoffeeShop.entity.Queue;
import com.ioiDigital.TheCoffeeShop.repository.QueueRepository;
import com.ioiDigital.TheCoffeeShop.service.OrderService;
import com.ioiDigital.TheCoffeeShop.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueueServiceImpl implements QueueService {
    @Autowired
    private QueueRepository queueRepository;
    @Override
    public Object updateSizeOfQueue(int maxSize) {
        return null;
    }
    @Autowired
    private OrderService orderService;

    @Override
    public Queue getQueueById(Long id) {
        return queueRepository.findById(id).orElse(null);
    }

    // Remove an order from a queue
    @Override
    public void removeOrderFromQueue(Order order) {
        // Get the queue that the order belongs to
        Queue queue = order.getQueue();
        // Check if the queue exists
        if (queue != null) {
            // Remove the order from the queue's list of orders
            queue.getOrders().remove(order);
            // Save the queue to the database
            queueRepository.save(queue);
        }
    }


    // Update the queue position and estimated waiting time of other orders in the queue
    @Override
    public void updateQueueDetails() {
        // Get all the queues from the database
        List<Queue> queues = queueRepository.findAll();
        // Loop through each queue
        for (Queue queue : queues) {
            // Get the list of orders in the queue
            List<Order> orders = queue.getOrders();
            // Loop through each order in the queue
            for (int i = 0; i < orders.size(); i++) {
                // Get the current order
                Order order = orders.get(i);
                // Set the queue position for the order as the index plus one
                order.setQueuePosition(i + 1);
                // Get the estimated waiting time for the order using the order service
                int estimatedWaitingTime = orderService.getEstimatedWaitingTime(order.getId());
                // Set the estimated waiting time for the order
                order.setEstimatedWaitingTime(estimatedWaitingTime);
                // Save the order to the database
                orderService.saveOrder(order);
            }
        }
    }
}
