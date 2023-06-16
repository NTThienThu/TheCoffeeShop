package com.ioiDigital.TheCoffeeShop.service.impl;

import com.ioiDigital.TheCoffeeShop.entity.Admin;
import com.ioiDigital.TheCoffeeShop.entity.Order;
import com.ioiDigital.TheCoffeeShop.entity.Queue;
import com.ioiDigital.TheCoffeeShop.repository.OrderRepository;
import com.ioiDigital.TheCoffeeShop.repository.QueueRepository;
import com.ioiDigital.TheCoffeeShop.service.AdminService;
import com.ioiDigital.TheCoffeeShop.service.OrderService;
import com.ioiDigital.TheCoffeeShop.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueueServiceImpl implements QueueService {
    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Queue getQueueByShopId(long id) {
        return queueRepository.findByCoffeeShop_Id(id).orElseThrow(() -> new RuntimeException("Not found queue"));
    }

    @Override
    public Queue getQueueById(Long id) {
        return queueRepository.findById(id).orElse(null);
    }

    @Override
    public Object updateSizeOfQueue(int maxSize) {
        Admin admin = adminService.getCurrentLogInAdmin();
        Queue queue = this.getQueueByShopId(admin.getCoffeeShop().getId());
        queue.setMaxQueueSize(maxSize);
        return null;
    }

    @Override
    public void updateQueueDetails() {

        List<Queue> queues = queueRepository.findAll();

        for (Queue queue : queues) {

            List<Order> orders = orderService.findAllByQueueId(queue.getId());

            for (int i = 0; i < orders.size(); i++) {

                Order order = orders.get(i);
                order.setQueuePosition(i + 1);

                int estimatedWaitingTime = orderService.getEstimatedWaitingTime(order.getId());
                order.setEstimatedWaitingTime(estimatedWaitingTime);

                orderRepository.save(order);
            }
        }
    }
}
