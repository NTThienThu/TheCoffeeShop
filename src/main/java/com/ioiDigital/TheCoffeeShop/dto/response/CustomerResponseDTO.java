package com.ioiDigital.TheCoffeeShop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerResponseDTO implements Serializable {
    private long id;
    private String name;
    private String mobileNumber;
    private String address;
    private int serviceCount;
    private UserResponseDTO userResponseDTO;
}
