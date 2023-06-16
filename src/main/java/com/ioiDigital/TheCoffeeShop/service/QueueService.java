package com.ioiDigital.TheCoffeeShop.service;

import com.ioiDigital.TheCoffeeShop.entity.Order;
import com.ioiDigital.TheCoffeeShop.entity.Queue;

public interface QueueService {
    Object updateSizeOfQueue(int maxSize);

    Queue getQueueByShopId(long id);

    Queue getQueueById(Long id);

    void updateQueueDetails(Long id);
}
