package com.ioiDigital.TheCoffeeShop.mapper;

import com.ioiDigital.TheCoffeeShop.dto.response.RoleResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.Role;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface RoleMapper {
	Role toEntity(RoleResponseDTO roleResponseDTO);

	RoleResponseDTO toDTO(Role role);
}
