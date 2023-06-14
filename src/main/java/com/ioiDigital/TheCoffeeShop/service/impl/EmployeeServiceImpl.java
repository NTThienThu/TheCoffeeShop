package com.ioiDigital.TheCoffeeShop.service.impl;

import com.ioiDigital.TheCoffeeShop.dto.request.EmployeeRegisterDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.EmployeeResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.Admin;
import com.ioiDigital.TheCoffeeShop.entity.ERole;
import com.ioiDigital.TheCoffeeShop.entity.Employee;
import com.ioiDigital.TheCoffeeShop.entity.User;
import com.ioiDigital.TheCoffeeShop.mapper.EmployeeMapper;
import com.ioiDigital.TheCoffeeShop.repository.CoffeeShopRepository;
import com.ioiDigital.TheCoffeeShop.repository.EmployeeRepository;
import com.ioiDigital.TheCoffeeShop.repository.RoleRepository;
import com.ioiDigital.TheCoffeeShop.service.AdminService;
import com.ioiDigital.TheCoffeeShop.service.EmployeeService;
import com.ioiDigital.TheCoffeeShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CoffeeShopRepository coffeeShopRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public EmployeeResponseDTO registerEmployee(EmployeeRegisterDTO employeeRegisterDTO) {

        Admin admin = adminService.getCurrentLogInAdmin();
        User user = userService.registerUser(employeeRegisterDTO.getUserRegisterDTO());
        user.setRole(roleRepository.findByName(ERole.EMPLOYEE.getRoleName()));

        Employee employee = new Employee();
        employee.setUser(user);
        employee.setCoffeeShop(coffeeShopRepository.findById(admin.getCoffeeShop().getId()));
        employeeRepository.save(employee);

        return employeeMapper.toDTO(employee);

    }

    @Override
    public Object updateEmployeeInfo(EmployeeRegisterDTO employeeRegisterDTO) {
        Employee employee = this.getCurrentLoginEmployee();
        employee.setName(employeeRegisterDTO.getName());
        employee.setPosition(employeeRegisterDTO.getPosition());
        employeeRepository.save(employee);
        return this.employeeMapper.toDTO(employee);
    }

    @Override
    public Employee getCurrentLoginEmployee() {
        return employeeRepository.findByUserId(userService.getCurrentLoginUser().getId())
                .orElseThrow(() -> new RuntimeException("Not found customer by this token"));
    }
}
