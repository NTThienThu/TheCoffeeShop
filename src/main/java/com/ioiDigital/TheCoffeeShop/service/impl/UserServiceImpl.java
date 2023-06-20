package com.ioiDigital.TheCoffeeShop.service.impl;

import com.ioiDigital.TheCoffeeShop.common.util.impl.Validation;
import com.ioiDigital.TheCoffeeShop.dto.request.ChangePasswordDTO;
import com.ioiDigital.TheCoffeeShop.dto.request.UserRegisterDTO;
import com.ioiDigital.TheCoffeeShop.entity.User;
import com.ioiDigital.TheCoffeeShop.mapper.UserMapper;
import com.ioiDigital.TheCoffeeShop.repository.AdminRepository;
import com.ioiDigital.TheCoffeeShop.repository.RoleRepository;
import com.ioiDigital.TheCoffeeShop.repository.UserRepository;
import com.ioiDigital.TheCoffeeShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Validation validation;

    @Override
    public User registerUser(UserRegisterDTO userRegisterDTO) {
        if (!this.userRepository.existsByUsername(userRegisterDTO.getUsername())) {
            User user = userMapper.toEntity(userRegisterDTO);
            user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
            user.setStatus(true);
            userRepository.save(user);
            return user;
        }
        else throw new IllegalArgumentException("username is exist");

    }

//    @Override
//    public User findById(long id) {
//        return userRepository.findById(id).orElseThrow();
//    }

    @Override
    public User getCurrentLoginUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Not found user by this token"));

        return user;
    }

    @Override
    public boolean changePassword(ChangePasswordDTO changePasswordDTO) {

        User user = this.getCurrentLoginUser();

        if (checkValidOldPassword(user.getPassword(), changePasswordDTO.getOldPassword())) {
            if (!validation.passwordValid(changePasswordDTO.getNewPassword()))
                throw new IllegalArgumentException("password invalid");
            user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("password incorrect");
        }

        return true;
    }

    @Override
    public boolean checkValidOldPassword(String oldPass, String confirmPass) {
        return passwordEncoder.matches(confirmPass, oldPass);
    }

}
