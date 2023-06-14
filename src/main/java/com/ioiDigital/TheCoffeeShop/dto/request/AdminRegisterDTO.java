package com.ioiDigital.TheCoffeeShop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRegisterDTO{
    @NotBlank
    private long shopId;

    @NotBlank
    private UserRegisterDTO userRegisterDTO;

    private String name;
    private String email;
    private String phone;
}
