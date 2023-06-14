package com.ioiDigital.TheCoffeeShop.mapper;

import com.ioiDigital.TheCoffeeShop.dto.request.ListMenuItemCreateDTO;
import com.ioiDigital.TheCoffeeShop.dto.request.MenuItemCreationDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.ListMenuItemResponseDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.MenuItemResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {

    MenuItem toEntity(MenuItemCreationDTO menuItemCreationDTO);

    MenuItemResponseDTO toDTO(MenuItem menuItem);

   // @Mapping(source = "menuItemCreationDTOS", target = "menuItems")
    List<MenuItem> toMenuItemList(List<MenuItemCreationDTO> menuItemCreationDTOS);

    List<MenuItemResponseDTO> toListDTO(List<MenuItem> menuItems);
}
