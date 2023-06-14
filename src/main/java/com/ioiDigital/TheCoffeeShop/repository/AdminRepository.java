package com.ioiDigital.TheCoffeeShop.repository;

import com.ioiDigital.TheCoffeeShop.entity.Admin;
import com.ioiDigital.TheCoffeeShop.entity.CoffeeShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByCoffeeShopId(Long aLong);

//    CoffeeShop findCoffeeShopByAdminId(long id);

        Optional<Admin> findById(Long aLong);

    Optional<Admin> findByUserId(long id);
}
