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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public OrderResponseDTO getOrderDetailById(Long orderId) {
        Order order = orderService.getOrderById(orderId);

        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);
        order.setOrderItems(orderItems);

        int queuePosition = orderService.getQueuePosition(orderId);
        int estimatedWaitingTime = orderService.getEstimatedWaitingTime(orderId);

        order.setQueuePosition(queuePosition);
        order.setEstimatedWaitingTime(estimatedWaitingTime);

        OrderResponseDTO orderResponseDTO = this.orderMapper.toDTO(order);
        orderResponseDTO.setCustomerName(order.getCustomer().getName());

        return orderResponseDTO;
    }

    @Override
    public int getQueuePosition(Long orderId) {


        Order order = orderRepository.findById(orderId).orElse(null);

        if (order != null && order.getStatus().equals(EStatusOrder.RECEIVED.getStatusOrder())) {

            List<Order> ordersBefore = orderRepository.findByStatusAndQueueIdAndOrderDateBefore(EStatusOrder.RECEIVED.getStatusOrder(), order.getQueue().getId(), order.getOrderDate());

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
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found order by this id"));
    }

    @Override
    public OrderResponseDTO createOrder(OrderCreateDTO orderCreateDTO) {

        Queue queue = queueService.getQueueByShopId(orderCreateDTO.getShopId());

        int orderInQueue = orderRepository.findByStatusAndQueueId(EStatusOrder.RECEIVED.getStatusOrder(), queue
                .getId()).size();
        if (orderInQueue < queue.getMaxQueueSize()) {

            Order order = orderMapper.toEntity(orderCreateDTO);

            Customer customer = customerService.getCurrentLogInCustomer();
            List<OrderItem> orderItemList = orderItemMapper.toListEntity(orderCreateDTO.getOrderItemCreateDTOS());

            orderItemList.forEach(orderItem -> {
                MenuItem menuItem = menuItemRepository.findById(orderItem.getMenuItem().getId())
                        .orElseThrow(() -> new RuntimeException("Menu item not found with ID: " + orderItem.getMenuItem().getId()));

                orderItem.setSubtotal(orderItem.getQuantity() * menuItem.getPrice());
                orderItem.setMenuItem(menuItem);
                orderItem.setOrder(order);
            });

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

            orderItemRepository.saveAll(orderItemList);

            List<Order> orders = this.findAllByStatusAndQueueId(EStatusOrder.RECEIVED.getStatusOrder(), queue.getId());

            for (int i = 0; i < orders.size(); i++) {

                Order order1 = orders.get(i);
                order1.setQueuePosition(i + 1);

                int estimatedWaitingTime = orderService.getEstimatedWaitingTime(order1.getId());
                order1.setEstimatedWaitingTime(estimatedWaitingTime);

//                orderRepository.save(order1);
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
    public List<Order> findAllByStatusAndQueueId(String status, Long id) {
        return orderRepository.findByStatusAndQueueId(status, id);
    }


    @Override
    public MessageResponse cancelOrder(Long orderId) {

        Order order = orderService.getOrderById(orderId);

        if (order != null && order.getStatus().equals(EStatusOrder.RECEIVED.getStatusOrder())) {

            order.setStatus(EStatusOrder.CANCEL.getStatusOrder());

            orderRepository.save(order);

            queueService.updateQueueDetails(order.getQueue().getId());
        } else throw new RuntimeException("only Pending Order can Cancel");
        return new MessageResponse("Cancel done");
    }

    @Override
    public OrderResponseDTO completedOrder(Long orderId) {
        Order order = orderService.getOrderById(orderId);

        if (order != null && order.getStatus().equals(EStatusOrder.PREPARING.getStatusOrder())) {

            order.setStatus(EStatusOrder.DONE.getStatusOrder());

            orderRepository.save(order);

            Customer customer = order.getCustomer();

            if (customer != null) {

                customer.setServiceCount(customer.getServiceCount() + 1);

                customerRepository.save(customer);
            }
        } else throw new RuntimeException("only Processing Order can Completed");
        return orderMapper.toDTO(order);
    }

    @Override
    public OrderResponseDTO serveCustomer(Long orderId) {

        Order order = orderService.getOrderById(orderId);

        if (order != null && order.getStatus().equals(EStatusOrder.RECEIVED.getStatusOrder())) {

            order.setStatus(EStatusOrder.PREPARING.getStatusOrder());
            order.setProcessedDate(LocalDateTime.now());

            orderRepository.save(order);

            queueService.updateQueueDetails(order.getQueue().getId());
        } else throw new RuntimeException("only Pending Order can Serve");
        return orderMapper.toDTO(order);
    }

}