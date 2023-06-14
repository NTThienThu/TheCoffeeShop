package com.ioiDigital.TheCoffeeShop.repository;

import com.ioiDigital.TheCoffeeShop.entity.GlobalAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalAdminRepository extends JpaRepository<GlobalAdmin, Long> {
}
