package com.ioiDigital.TheCoffeeShop.mapper;

import com.ioiDigital.TheCoffeeShop.dto.request.OrderCreateDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.OrderResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring",uses = { OrderItemMapper.class, MenuItemMapper.class})
public interface OrderMapper {
    @Mapping(source = "orderItems",target = "orderItemResponseDTOS")
    OrderResponseDTO toDTO(Order order);

    Order toEntity(OrderCreateDTO orderCreateDTO);



    Order toEntity (OrderResponseDTO orderResponseDTO);
}
