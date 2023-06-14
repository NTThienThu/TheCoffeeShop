package com.ioiDigital.TheCoffeeShop.mapper;

import com.ioiDigital.TheCoffeeShop.dto.request.MenuItemCreationDTO;
import com.ioiDigital.TheCoffeeShop.dto.request.OrderItemCreateDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.MenuItemResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.MenuItem;
import com.ioiDigital.TheCoffeeShop.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring",uses = { MenuItemMapper.class})
public interface OrderItemMapper {

    OrderItem toEntity(OrderItemCreateDTO orderItemCreateDTO);

    List<OrderItem> toListEntity(List<OrderItemCreateDTO> orderItemCreateDTOS);


}
