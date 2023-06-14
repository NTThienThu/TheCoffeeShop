package com.ioiDigital.TheCoffeeShop.repository;

import com.ioiDigital.TheCoffeeShop.dto.request.SearchCoffeeShopDTO;
import com.ioiDigital.TheCoffeeShop.entity.CoffeeShop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoffeeShopRepository extends JpaRepository<CoffeeShop, Long> {

    CoffeeShop findById(long id);

    boolean existsById(Long aLong);

    boolean existsByName(String name);

    @Query(value = "SELECT cfs FROM CoffeeShop cfs WHERE cfs.status = true ORDER BY cfs.id ASC")
    Page<CoffeeShop> findAllActive(Pageable pageable);

    Page<CoffeeShop> findAll(Specification<CoffeeShop> filter, Pageable pageable);
    List<CoffeeShop> findByStatus(boolean status);

//    CoffeeShop findByAdminId(long id);


}
