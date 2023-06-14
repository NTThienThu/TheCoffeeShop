package com.ioiDigital.TheCoffeeShop.dto.response;

import com.ioiDigital.TheCoffeeShop.entity.CoffeeShop;
import com.ioiDigital.TheCoffeeShop.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponseDTO {
    private long id;

    private UserResponseDTO userResponseDTO;

    private String name;
    private String email;
    private String phone;

    private CoffeeShopResponseDTO coffeeShopResponseDTO;
}
