package com.ioiDigital.TheCoffeeShop.service;

import com.ioiDigital.TheCoffeeShop.dto.request.ChangePasswordDTO;
import com.ioiDigital.TheCoffeeShop.dto.request.UserRegisterDTO;
import com.ioiDigital.TheCoffeeShop.entity.User;

public interface UserService {
    User registerUser(UserRegisterDTO registerRequest);

//    User findById(long id);

    User getCurrentLoginUser();

    boolean changePassword(ChangePasswordDTO passwordChangeDTO);

    boolean checkValidOldPassword(String oldPass, String confirmPass);
}
