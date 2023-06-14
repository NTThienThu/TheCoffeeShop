package com.ioiDigital.TheCoffeeShop.mapper;

import com.ioiDigital.TheCoffeeShop.dto.response.AdminResponseDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.EmployeeResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.Admin;
import com.ioiDigital.TheCoffeeShop.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class, RoleMapper.class, CoffeeShopMapper.class})
public interface EmployeeMapper {
    @Mapping(source = "user", target = "userResponseDTO")
    @Mapping(source = "coffeeShop", target = "coffeeShopResponseDTO")
    EmployeeResponseDTO toDTO(Employee employee);
}
