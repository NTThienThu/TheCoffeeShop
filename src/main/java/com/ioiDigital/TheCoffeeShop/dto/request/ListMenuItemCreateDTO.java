package com.ioiDigital.TheCoffeeShop.dto.request;

import com.ioiDigital.TheCoffeeShop.entity.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListMenuItemCreateDTO {
    private List<MenuItemCreationDTO> menuItemCreationDTOS;
}
