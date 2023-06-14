package com.ioiDigital.TheCoffeeShop.controllers;

import com.ioiDigital.TheCoffeeShop.dto.request.AdminRegisterDTO;
import com.ioiDigital.TheCoffeeShop.dto.request.UserRegisterDTO;
import com.ioiDigital.TheCoffeeShop.repository.GlobalAdminRepository;
import com.ioiDigital.TheCoffeeShop.repository.RoleRepository;
import com.ioiDigital.TheCoffeeShop.repository.UserRepository;
import com.ioiDigital.TheCoffeeShop.service.AdminService;
import com.ioiDigital.TheCoffeeShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/global-admin")
//@PreAuthorize("hasRole('global_admin')")
public class GlobalAdminController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private GlobalAdminRepository globalAdminRepository;




}