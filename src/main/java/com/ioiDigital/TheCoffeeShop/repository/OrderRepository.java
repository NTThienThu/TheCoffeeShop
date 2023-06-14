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

    // Find an order by id and customer id
    Optional<Order> findByIdAndCustomerId(Long id, Long customerId);

    // Find a list of orders by status and created time before a given time
    List<Order> findByStatusAndCreatedTimeBefore(String status, LocalDateTime createdTime);

    // Get the average processing time of completed orders using a custom query
    @Query("select avg(o.processedTime - o.createdTime) from Order o where o.status = 'completed'")
    Double getAverageProcessingTime();

    Optional<Order> findByIdAndQueueId(Long id, Long queueId);



}
