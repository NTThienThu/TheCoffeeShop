package com.ioiDigital.TheCoffeeShop.mapper;

import com.ioiDigital.TheCoffeeShop.dto.request.CustomerRegisterDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.CustomerResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",  uses = { UserMapper.class, RoleMapper.class})
public interface CustomerMapper {

    @Mapping(source = "userRegisterDTO", target = "user")
    Customer toEntity (CustomerRegisterDTO customerRegisterDTO);

    @Mapping(source = "user", target = "userResponseDTO")
    CustomerResponseDTO toDTO (Customer customer);
}
