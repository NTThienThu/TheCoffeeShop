package com.ioiDigital.TheCoffeeShop.repository;

import com.ioiDigital.TheCoffeeShop.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ioiDigital.TheCoffeeShop.entity.ERole;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findByName(String name);
}
