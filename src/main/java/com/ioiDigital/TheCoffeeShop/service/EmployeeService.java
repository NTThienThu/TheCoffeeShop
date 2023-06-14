package com.ioiDigital.TheCoffeeShop.service;

import com.ioiDigital.TheCoffeeShop.dto.request.EmployeeRegisterDTO;
import com.ioiDigital.TheCoffeeShop.entity.Employee;

public interface EmployeeService {
    Object registerEmployee(EmployeeRegisterDTO employeeRegisterDTO);

    Object updateEmployeeInfo(EmployeeRegisterDTO employeeRegisterDTO);

    Employee getCurrentLoginEmployee();
}
