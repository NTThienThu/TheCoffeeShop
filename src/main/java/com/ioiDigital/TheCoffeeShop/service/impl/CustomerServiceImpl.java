package com.ioiDigital.TheCoffeeShop.service.impl;

import com.ioiDigital.TheCoffeeShop.dto.request.CustomerRegisterDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.CustomerResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.Customer;
import com.ioiDigital.TheCoffeeShop.entity.ERole;
import com.ioiDigital.TheCoffeeShop.entity.User;
import com.ioiDigital.TheCoffeeShop.mapper.CustomerMapper;
import com.ioiDigital.TheCoffeeShop.mapper.UserMapper;
import com.ioiDigital.TheCoffeeShop.repository.CustomerRepository;
import com.ioiDigital.TheCoffeeShop.repository.RoleRepository;
import com.ioiDigital.TheCoffeeShop.repository.UserRepository;
import com.ioiDigital.TheCoffeeShop.service.CustomerService;
import com.ioiDigital.TheCoffeeShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public CustomerResponseDTO create(CustomerRegisterDTO customerRegisterDTO) {

        User user = userService.registerUser(customerRegisterDTO.getUserRegisterDTO());
        user.setRole(roleRepository.findByName(ERole.CUSTOMER.getRoleName()));
        Customer customer = this.customerMapper.toEntity(customerRegisterDTO);
        customer.setUser(user);
        CustomerResponseDTO customerResponseDTO = customerMapper.toDTO(customerRepository.save(customer));

        return customerResponseDTO;
    }

    @Override
    public CustomerResponseDTO update(CustomerRegisterDTO customerRegisterDTO) {

        Customer customer = this.getCurrentLogInCustomer();
        customer.setName(customerRegisterDTO.getName());
        customer.setMobileNumber(customerRegisterDTO.getMobileNumber());
        customer.setAddress(customerRegisterDTO.getAddress());
        return this.customerMapper.toDTO(customerRepository.save(customer));

    }

    @Override
    public Customer getCurrentLogInCustomer(){
        return customerRepository.findByUserId(this.userService.getCurrentLoginUser().getId())
                .orElseThrow(() -> new RuntimeException("Not found customer by this token"));
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

}
