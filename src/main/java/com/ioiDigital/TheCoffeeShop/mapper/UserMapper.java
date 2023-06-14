package com.ioiDigital.TheCoffeeShop.mapper;

import com.ioiDigital.TheCoffeeShop.dto.request.UserRegisterDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.UserResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { RoleMapper.class})
public interface UserMapper {
    @Mapping(source = "username", target = "username")
    User toEntity(UserRegisterDTO userRegisterDTO);

    @Mapping(source = "role", target = "roleDTO")
    UserResponseDTO toDTO(User user);
}
