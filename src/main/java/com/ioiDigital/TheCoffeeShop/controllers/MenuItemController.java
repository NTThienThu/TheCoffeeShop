package com.ioiDigital.TheCoffeeShop.controllers;

import com.ioiDigital.TheCoffeeShop.dto.request.AdminRegisterDTO;
import com.ioiDigital.TheCoffeeShop.dto.request.CoffeeShopCreateDTO;
import com.ioiDigital.TheCoffeeShop.dto.request.ListMenuItemCreateDTO;
import com.ioiDigital.TheCoffeeShop.service.MenuItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menu-item")
public class MenuItemController {
    @Autowired
    private MenuItemService menuItemService;

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity createMenu(@RequestBody ListMenuItemCreateDTO listMenuItemCreateDTO) {
        return new ResponseEntity<>(this.menuItemService.createMenu(listMenuItemCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity updateMenu(@RequestBody ListMenuItemCreateDTO listMenuItemCreateDTO) {
        return new ResponseEntity<>(this.menuItemService.updateMenu(listMenuItemCreateDTO.getMenuItemCreationDTOS()), HttpStatus.CREATED);
    }
}
