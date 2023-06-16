package com.ioiDigital.TheCoffeeShop.repository;

import com.ioiDigital.TheCoffeeShop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndCustomerId(Long id, Long customerId);

    List<Order> findByStatusAndQueueIdAndOrderDateBefore(String status, Long id, LocalDateTime createdTime);

    Optional<Order> findByIdAndQueueId(Long id, Long queueId);

    List<Order> findAllByQueueId(Long id);

    List<Order> findByStatusAndQueueId(String status, Long id);


}
