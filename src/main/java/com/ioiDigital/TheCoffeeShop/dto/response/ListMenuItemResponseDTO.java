package com.ioiDigital.TheCoffeeShop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListMenuItemResponseDTO {
    private List<MenuItemResponseDTO> menuItemResponseDTOS;
}
