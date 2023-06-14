package com.ioiDigital.TheCoffeeShop.mapper;

import com.ioiDigital.TheCoffeeShop.dto.request.UserRegisterDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.AdminResponseDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.UserResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.Admin;
import com.ioiDigital.TheCoffeeShop.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UserMapper.class, RoleMapper.class, CoffeeShopMapper.class})
public interface AdminMapper {
//    @Mapping(ignore = true, target = "username")
//    Admin toEntity(Admin userRegisterDTO);

//    @Mapping(source = "role", target = "roleDTO")
    @Mapping(source = "user", target = "userResponseDTO")
    @Mapping(source = "coffeeShop", target = "coffeeShopResponseDTO")
    AdminResponseDTO toDTO(Admin admin);
}
