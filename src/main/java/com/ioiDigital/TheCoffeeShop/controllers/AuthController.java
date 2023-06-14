package com.ioiDigital.TheCoffeeShop.controllers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ioiDigital.TheCoffeeShop.dto.request.ChangePasswordDTO;
import com.ioiDigital.TheCoffeeShop.dto.request.LoginRequest;
import com.ioiDigital.TheCoffeeShop.entity.User;
import com.ioiDigital.TheCoffeeShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ioiDigital.TheCoffeeShop.dto.response.JwtResponse;
import com.ioiDigital.TheCoffeeShop.repository.RoleRepository;
import com.ioiDigital.TheCoffeeShop.repository.UserRepository;
import com.ioiDigital.TheCoffeeShop.security.jwt.JwtUtils;
import com.ioiDigital.TheCoffeeShop.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        User user = userService.getCurrentLoginUser();
        if (!user.isStatus()) throw new RuntimeException("Your account not active");
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = Collections.singletonList(userDetails.getAuthorities()).toString();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                role));
    }

    @PreAuthorize(value = "isAuthenticated()")
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO passwordChangeDTO) {
        this.userService.changePassword(passwordChangeDTO);

        return ResponseEntity.ok(HttpServletResponse.SC_OK);

    }
}
