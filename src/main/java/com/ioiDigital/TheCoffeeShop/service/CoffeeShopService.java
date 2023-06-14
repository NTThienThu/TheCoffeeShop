package com.ioiDigital.TheCoffeeShop.service;

import com.ioiDigital.TheCoffeeShop.dto.request.CoffeeShopCreateDTO;
import com.ioiDigital.TheCoffeeShop.dto.request.SearchCoffeeShopDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.CoffeeShopResponseDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.PaginationDTO;

public interface CoffeeShopService {

    CoffeeShopResponseDTO createCoffeeShop(CoffeeShopCreateDTO coffeeShopRegisterDTO);

    CoffeeShopResponseDTO updateCoffeeShop(CoffeeShopCreateDTO coffeeShopRegisterDTO);

    PaginationDTO findAllActive (int no, int limit);

    PaginationDTO searchCoffeeShop(SearchCoffeeShopDTO searchCoffeeShopDTO, int no, int limit);




}
