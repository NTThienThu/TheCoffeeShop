package com.ioiDigital.TheCoffeeShop.controllers;

import com.ioiDigital.TheCoffeeShop.dto.request.AdminRegisterDTO;
import com.ioiDigital.TheCoffeeShop.dto.request.EmployeeRegisterDTO;
import com.ioiDigital.TheCoffeeShop.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @PostMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity createEmployee(@RequestBody EmployeeRegisterDTO employeeRegisterDTO) {
        return new ResponseEntity<>(this.employeeService.registerEmployee(employeeRegisterDTO), HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('Employee')")
    public ResponseEntity updateEmployee(@RequestBody EmployeeRegisterDTO employeeRegisterDTO) {
        return new ResponseEntity<>(this.employeeService.updateEmployeeInfo(employeeRegisterDTO), HttpStatus.CREATED);
    }
}
