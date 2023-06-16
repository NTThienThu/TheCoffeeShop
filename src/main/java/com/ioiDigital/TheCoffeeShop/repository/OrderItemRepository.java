package com.ioiDigital.TheCoffeeShop.repository;

import com.ioiDigital.TheCoffeeShop.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
