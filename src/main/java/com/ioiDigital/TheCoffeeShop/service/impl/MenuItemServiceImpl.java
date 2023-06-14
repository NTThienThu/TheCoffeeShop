package com.ioiDigital.TheCoffeeShop.service.impl;

import com.ioiDigital.TheCoffeeShop.dto.request.ListMenuItemCreateDTO;
import com.ioiDigital.TheCoffeeShop.dto.request.MenuItemCreationDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.ListMenuItemResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.Admin;
import com.ioiDigital.TheCoffeeShop.entity.CoffeeShop;
import com.ioiDigital.TheCoffeeShop.entity.MenuItem;
import com.ioiDigital.TheCoffeeShop.mapper.MenuItemMapper;
import com.ioiDigital.TheCoffeeShop.repository.AdminRepository;
import com.ioiDigital.TheCoffeeShop.repository.CoffeeShopRepository;
import com.ioiDigital.TheCoffeeShop.repository.MenuItemRepository;
import com.ioiDigital.TheCoffeeShop.service.AdminService;
import com.ioiDigital.TheCoffeeShop.service.MenuItemService;
import com.ioiDigital.TheCoffeeShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements MenuItemService {
    @Autowired
    private UserService userService;

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private MenuItemMapper menuItemMapper;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private CoffeeShopRepository coffeeShopRepository;

    @Autowired
    private AdminService adminService;

    @Override
    public ListMenuItemResponseDTO createMenu(ListMenuItemCreateDTO listMenuItemCreateDTO) {
        Admin admin = adminService.getCurrentLogInAdmin();
        CoffeeShop coffeeShop = coffeeShopRepository.findById(admin.getCoffeeShop().getId());

        List<MenuItem> menuItems = this.menuItemMapper.toMenuItemList(listMenuItemCreateDTO.getMenuItemCreationDTOS());
        menuItems = menuItems.stream()
                .map(obj -> {
                    obj.setCoffeeShop(coffeeShop);
                    return obj;
                })
                .collect(Collectors.toList());
        this.menuItemRepository.saveAll(menuItems);

        ListMenuItemResponseDTO listMenuItemResponseDTO = new ListMenuItemResponseDTO();
        listMenuItemResponseDTO.setMenuItemResponseDTOS(this.menuItemMapper.toListDTO(menuItems));

        return listMenuItemResponseDTO;
    }

    @Override
    public ListMenuItemResponseDTO updateMenu(List<MenuItemCreationDTO> menuItemDTOs) {
        Admin admin = adminService.getCurrentLogInAdmin();
        CoffeeShop coffeeShop = coffeeShopRepository.findById(admin.getCoffeeShop().getId());
        List<MenuItem> existingMenuItems = menuItemRepository.findAllByCoffeeShop_Id(coffeeShop.getId());

        for (MenuItem existingMenuItem : existingMenuItems) {
            boolean menuItemExists = menuItemDTOs.stream()
                    .anyMatch(menuItemDTO -> menuItemDTO.getItemName().equals(existingMenuItem.getItemName()));
            if (!menuItemExists) {
                menuItemRepository.deleteById(existingMenuItem.getId());
            }
        }

        for (MenuItemCreationDTO menuItemDTO : menuItemDTOs) {
            Optional<MenuItem> existingMenuItem = existingMenuItems.stream()
                    .filter(item -> item.getItemName().equals(menuItemDTO.getItemName()))
                    .findFirst();

            if (existingMenuItem.isPresent()) {

                MenuItem menuItem = existingMenuItem.get();
                menuItem.setPrice(menuItemDTO.getPrice());
                menuItemRepository.save(menuItem);
            } else {

                MenuItem newMenuItem = new MenuItem();
                newMenuItem.setCoffeeShop(coffeeShop);
                newMenuItem.setItemName(menuItemDTO.getItemName());
                newMenuItem.setPrice(menuItemDTO.getPrice());
                menuItemRepository.save(newMenuItem);
            }
        }

        ListMenuItemResponseDTO listMenuItemResponseDTO = new ListMenuItemResponseDTO();
        List<MenuItem> updatedMenuItems = menuItemRepository.findAllByCoffeeShop_Id(coffeeShop.getId());
        listMenuItemResponseDTO.setMenuItemResponseDTOS(this.menuItemMapper.toListDTO(updatedMenuItems));

        return listMenuItemResponseDTO;
    }
}
