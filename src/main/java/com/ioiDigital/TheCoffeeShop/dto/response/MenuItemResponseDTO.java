package com.ioiDigital.TheCoffeeShop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemResponseDTO {
    private long id;
    private String itemName;
    private double price;
}
