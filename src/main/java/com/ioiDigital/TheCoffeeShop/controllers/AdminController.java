package com.ioiDigital.TheCoffeeShop.controllers;

import com.ioiDigital.TheCoffeeShop.dto.request.AdminRegisterDTO;
import com.ioiDigital.TheCoffeeShop.dto.request.CoffeeShopCreateDTO;
import com.ioiDigital.TheCoffeeShop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping
    @PreAuthorize("hasRole('Global_Admin')")
    public ResponseEntity createAdmin(@RequestBody AdminRegisterDTO adminRegisterDTO) {
        return new ResponseEntity<>(this.adminService.registerAdmin(adminRegisterDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity updateAdmin(@RequestBody AdminRegisterDTO adminRegisterDTO) {
        return new ResponseEntity<>(this.adminService.updateAdminInfo(adminRegisterDTO), HttpStatus.CREATED);
    }
}
