package com.ioiDigital.TheCoffeeShop.service;

import com.ioiDigital.TheCoffeeShop.dto.request.AdminRegisterDTO;
import com.ioiDigital.TheCoffeeShop.dto.request.UserRegisterDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.AdminResponseDTO;
import com.ioiDigital.TheCoffeeShop.entity.Admin;

public interface AdminService {
//    Object registerAdmin(UserRegisterDTO registerRequest);

    AdminResponseDTO registerAdmin(AdminRegisterDTO adminRegisterDTO);

    Object updateAdminInfo(AdminRegisterDTO adminRegisterDTO);


    Admin getCurrentLogInAdmin();
}
