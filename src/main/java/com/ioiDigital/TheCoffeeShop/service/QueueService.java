package com.ioiDigital.TheCoffeeShop.service;

import com.ioiDigital.TheCoffeeShop.entity.Order;
import com.ioiDigital.TheCoffeeShop.entity.Queue;

public interface QueueService {
    Object updateSizeOfQueue(int maxSize);

    // Get a queue by id
    Queue getQueueById(Long id);

    // Remove an order from a queue
    void removeOrderFromQueue(Order order);

    // Update the queue position and estimated waiting time of other orders in the queue
    void updateQueueDetails();
}
