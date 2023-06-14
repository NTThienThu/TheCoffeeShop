package com.ioiDigital.TheCoffeeShop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO {
    private long id;

    private UserResponseDTO userResponseDTO;

    private String name;
    private String position;

    private CoffeeShopResponseDTO coffeeShopResponseDTO;
}
