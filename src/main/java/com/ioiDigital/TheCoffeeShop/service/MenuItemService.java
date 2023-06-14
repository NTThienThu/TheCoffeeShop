package com.ioiDigital.TheCoffeeShop.service;

import com.ioiDigital.TheCoffeeShop.dto.request.AdminRegisterDTO;
import com.ioiDigital.TheCoffeeShop.dto.request.ListMenuItemCreateDTO;
import com.ioiDigital.TheCoffeeShop.dto.request.MenuItemCreationDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.ListMenuItemResponseDTO;

import java.util.List;

public interface MenuItemService {

    Object createMenu(ListMenuItemCreateDTO listMenuItemCreateDTO);

       //    @Override
//    public ListMenuItemResponseDTO updateMenu(ListMenuItemCreateDTO listMenuItemCreateDTO) {
//        Admin admin = adminService.getCurrentAdmin();
//        List<MenuItem> menuItems = this.menuItemMapper.toMenuItemList(listMenuItemCreateDTO.getMenuItemCreationDTOS());
//        for (MenuItem menuItem: menuItems){
//            if ()
//        }
//        return null;
//    }
    ListMenuItemResponseDTO updateMenu(List<MenuItemCreationDTO> menuItemDTOs);
}
