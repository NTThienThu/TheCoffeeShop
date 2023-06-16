package com.ioiDigital.TheCoffeeShop.dto.request;

import com.ioiDigital.TheCoffeeShop.entity.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemCreateDTO {
    private MenuItem menuItem;

    private int quantity;
    private double subtotal;
}
