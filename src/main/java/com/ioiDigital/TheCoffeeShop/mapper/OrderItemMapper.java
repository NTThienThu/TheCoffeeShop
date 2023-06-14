package com.ioiDigital.TheCoffeeShop.mapper;

import com.ioiDigital.TheCoffeeShop.dto.request.MenuItemCreationDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.MenuItemResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.MenuItem;
import com.ioiDigital.TheCoffeeShop.entity.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem toEntity(OrderItem menuItemCreationDTO);

    MenuItemResponseDTO toDTO(MenuItem menuItem);

    // @Mapping(source = "menuItemCreationDTOS", target = "menuItems")
    List<MenuItem> toMenuItemList(List<MenuItemCreationDTO> menuItemCreationDTOS);

    List<MenuItemResponseDTO> toListDTO(List<MenuItem> menuItems);
}
