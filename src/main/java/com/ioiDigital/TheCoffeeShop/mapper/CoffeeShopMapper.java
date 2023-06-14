package com.ioiDigital.TheCoffeeShop.mapper;

import com.ioiDigital.TheCoffeeShop.dto.request.CoffeeShopCreateDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.AdminResponseDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.CoffeeShopResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.Admin;
import com.ioiDigital.TheCoffeeShop.entity.CoffeeShop;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoffeeShopMapper {

    CoffeeShopResponseDTO toDTO(CoffeeShop coffeeShop);

    CoffeeShop toEntity(CoffeeShopCreateDTO coffeeShopCreateDTO);
}
