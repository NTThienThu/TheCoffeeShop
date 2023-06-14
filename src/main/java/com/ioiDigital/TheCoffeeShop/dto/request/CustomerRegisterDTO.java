package com.ioiDigital.TheCoffeeShop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegisterDTO implements Serializable {
    private String name;
    private String mobileNumber;
    private String address;
    private UserRegisterDTO userRegisterDTO;

}
