package com.ioiDigital.TheCoffeeShop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeShopResponseDTO {
    private int id;
    private String name;
    private String location;
    private String contactDetails;
    private String openingTime;
    private String closingTime;
    private boolean status;
    private long createdBy;
}
