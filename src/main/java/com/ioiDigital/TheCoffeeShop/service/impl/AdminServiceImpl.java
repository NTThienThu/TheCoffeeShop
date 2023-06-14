package com.ioiDigital.TheCoffeeShop.service.impl;

import com.ioiDigital.TheCoffeeShop.dto.request.AdminRegisterDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.AdminResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.Admin;
import com.ioiDigital.TheCoffeeShop.entity.ERole;
import com.ioiDigital.TheCoffeeShop.entity.User;
import com.ioiDigital.TheCoffeeShop.mapper.AdminMapper;
import com.ioiDigital.TheCoffeeShop.repository.AdminRepository;
import com.ioiDigital.TheCoffeeShop.repository.CoffeeShopRepository;
import com.ioiDigital.TheCoffeeShop.repository.RoleRepository;
import com.ioiDigital.TheCoffeeShop.service.AdminService;
import com.ioiDigital.TheCoffeeShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserService userService;

    @Autowired
    private CoffeeShopRepository coffeeShopRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public AdminResponseDTO registerAdmin(AdminRegisterDTO adminRegisterDTO) {
        if (!adminRepository.existsByCoffeeShopId(adminRegisterDTO.getShopId())) {

            User user = userService.registerUser(adminRegisterDTO.getUserRegisterDTO());
            user.setRole(roleRepository.findByName(ERole.ADMIN.getRoleName()));

            Admin admin = new Admin();
            admin.setUser(user);
            admin.setCoffeeShop(coffeeShopRepository.findById(adminRegisterDTO.getShopId()));
            adminRepository.save(admin);

            return adminMapper.toDTO(admin);

        }
        throw new RuntimeException("The coffee shop had admin");

    }

    @Override
    public AdminResponseDTO updateAdminInfo(AdminRegisterDTO adminRegisterDTO) {
        Admin admin = adminService.getCurrentLogInAdmin();
        admin.setName(adminRegisterDTO.getName());
        admin.setEmail(adminRegisterDTO.getEmail());
        admin.setPhone(adminRegisterDTO.getPhone());
        adminRepository.save(admin);
        return this.adminMapper.toDTO(admin);
    }

    @Override
    public Admin getCurrentLogInAdmin() {
        return adminRepository.findByUserId(userService.getCurrentLoginUser().getId()).
        orElseThrow(() -> new RuntimeException("Not found admin by this token"));
    }

}
