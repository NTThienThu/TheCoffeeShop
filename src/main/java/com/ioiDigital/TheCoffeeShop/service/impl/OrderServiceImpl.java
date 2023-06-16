package com.ioiDigital.TheCoffeeShop.service.impl;

import com.ioiDigital.TheCoffeeShop.dto.request.OrderCreateDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.MessageResponse;
import com.ioiDigital.TheCoffeeShop.dto.response.OrderResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.*;
import com.ioiDigital.TheCoffeeShop.mapper.OrderItemMapper;
import com.ioiDigital.TheCoffeeShop.mapper.OrderMapper;
import com.ioiDigital.TheCoffeeShop.repository.*;
import com.ioiDigital.TheCoffeeShop.service.CustomerService;
import com.ioiDigital.TheCoffeeShop.service.OrderService;
import com.ioiDigital.TheCoffeeShop.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
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
    private OrderService orderService;

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private QueueService queueService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public Order getOrderByIdAndCustomerId(Long orderId, Long customerId) {
        Order order = orderRepository.findByIdAndCustomerId(orderId, customerId).orElse(null);

        if (order == null) {
            throw new RuntimeException("Not found order");
        }

        int queuePosition = orderService.getQueuePosition(orderId);
        int estimatedWaitingTime = orderService.getEstimatedWaitingTime(orderId);

        order.setQueuePosition(queuePosition);
        order.setEstimatedWaitingTime(estimatedWaitingTime);
        return order;
    }

    @Override
    public int getQueuePosition(Long orderId) {

        Order order = orderRepository.findById(orderId).orElse(null);

        if (order != null && order.getStatus().equals(EStatusOrder.RECEIVED.getStatusOrder())) {

            List<Order> ordersBefore = orderRepository.findByStatusAndOrderDateBefore(EStatusOrder.RECEIVED.getStatusOrder(), order.getOrderDate());

            return ordersBefore.size() + 1;
        } else {

            return -1;
        }
    }

    @Override
    public int getEstimatedWaitingTime(Long orderId) {

        int queuePosition = getQueuePosition(orderId);

        if (queuePosition > 0) {
            // default waiting time of 1 order = 3 minutes
            return queuePosition * 3;
        } else {
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
    public OrderResponseDTO createOrder(OrderCreateDTO orderCreateDTO) {

        Queue queue = queueService.getQueueByShopId(orderCreateDTO.getShopId());
        int orderInQueue = orderRepository.findByStatusAndOrderDateBefore(EStatusOrder.RECEIVED.getStatusOrder(), LocalDateTime.now()).size();
        if (orderInQueue < queue.getMaxQueueSize()) {

            Customer customer = customerService.getCurrentLogInCustomer();
            List<OrderItem> orderItemList = orderItemMapper.toListEntity(orderCreateDTO.getOrderItemCreateDTOS());

            orderItemList.forEach(orderItem -> {
                MenuItem menuItem = menuItemRepository.findById(orderItem.getMenuItem().getId())
                        .orElseThrow(() -> new RuntimeException("Menu item not found with ID: " + orderItem.getMenuItem().getId()));

                orderItem.setSubtotal(orderItem.getQuantity() * menuItem.getPrice());
                orderItem.setMenuItem(menuItem);
            });

            orderItemRepository.saveAll(orderItemList);

            Order order = orderMapper.toEntity(orderCreateDTO);

            order.setQueue(queueService.getQueueByShopId(orderCreateDTO.getShopId()));
            order.setOrderItems(orderItemList);
            order.setCustomer(customer);
            order.setStatus(EStatusOrder.RECEIVED.getStatusOrder());
            order.setNote(orderCreateDTO.getNote());
            order.setOrderDate(LocalDateTime.now());

            double totalPrice = orderItemList.stream()
                    .mapToDouble(OrderItem::getSubtotal)
                    .sum();
            order.setTotalAmount(totalPrice);

            orderRepository.save(order);

            List<Order> orders = this.findAllByQueueId(queue.getId());

            for (int i = 0; i < orders.size(); i++) {

                Order order1 = orders.get(i);
                order1.setQueuePosition(i + 1);

                int estimatedWaitingTime = orderService.getEstimatedWaitingTime(order1.getId());
                order1.setEstimatedWaitingTime(estimatedWaitingTime);

                orderRepository.save(order1);
            }
            OrderResponseDTO orderResponseDTO = this.orderMapper.toDTO(order);
            orderResponseDTO.setCustomerName(customer.getName());

            return orderResponseDTO;
        }
        throw new RuntimeException("full queue");
    }

    @Override
    public List<Order> findAllByQueueId(Long id) {
        return orderRepository.findAllByQueueId(id);
    }

    @Override
    public MessageResponse cancelOrder(Long orderId, Long customerId) {
        Order order = orderService.getOrderByIdAndCustomerId(orderId, customerId);
        if (order != null && order.getStatus().equals(EStatusOrder.CANCEL.getStatusOrder())) {

            queueService.removeOrderFromQueue(order);

            order.setStatus(EStatusOrder.CANCEL.getStatusOrder());

            orderRepository.save(order);

            queueService.updateQueueDetails();
        }
        return new MessageResponse("Delete done");
    }

    @Override
    public OrderResponseDTO completedOrder(Long orderId) {
        Order order = orderService.getOrderById(orderId);

        if (order != null && order.getStatus().equals(EStatusOrder.DONE.getStatusOrder())) {

            order.setStatus(EStatusOrder.DONE.getStatusOrder());

            orderRepository.save(order);

            Customer customer = order.getCustomer();

            if (customer != null) {

                customer.setServiceCount(customer.getServiceCount() + 1);

                customerRepository.save(customer);
            }
        }
        return orderMapper.toDTO(order);
    }

    @Override
    public OrderResponseDTO serveCustomer(Long orderId, Long queueId) {
        Order order = orderService.getOrderByIdAndQueueId(orderId, queueId);
        if (order != null && order.getStatus().equals(EStatusOrder.RECEIVED.getStatusOrder())) {
            queueService.removeOrderFromQueue(order);
            order.setStatus(EStatusOrder.PREPARING.getStatusOrder());
            order.setProcessedDate(LocalDateTime.now());
            orderRepository.save(order);
        }
        return orderMapper.toDTO(order);
    }

}