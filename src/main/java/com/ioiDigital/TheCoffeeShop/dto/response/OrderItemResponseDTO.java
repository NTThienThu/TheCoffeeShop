package com.ioiDigital.TheCoffeeShop.dto.response;

import com.ioiDigital.TheCoffeeShop.entity.MenuItem;
import com.ioiDigital.TheCoffeeShop.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {
    private long id;

    private MenuItemResponseDTO menuItemResponseDTO;

    private int quantity;
    private double subtotal;
}
