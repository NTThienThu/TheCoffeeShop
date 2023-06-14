package com.ioiDigital.TheCoffeeShop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleResponseDTO implements Serializable {
    private long id;
    private String name;
}
