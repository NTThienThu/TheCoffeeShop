package com.ioiDigital.TheCoffeeShop.dto.request;

import com.ioiDigital.TheCoffeeShop.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDTO {
    private List<OrderItem> orderItems;

    private String note;

}
