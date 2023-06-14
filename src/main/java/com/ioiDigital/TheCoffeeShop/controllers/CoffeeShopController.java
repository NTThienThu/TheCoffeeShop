package com.ioiDigital.TheCoffeeShop.controllers;

import com.ioiDigital.TheCoffeeShop.constant.PageDefault;
import com.ioiDigital.TheCoffeeShop.dto.request.CoffeeShopCreateDTO;
import com.ioiDigital.TheCoffeeShop.dto.request.SearchCoffeeShopDTO;
import com.ioiDigital.TheCoffeeShop.repository.CoffeeShopRepository;
import com.ioiDigital.TheCoffeeShop.service.CoffeeShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coffee-shop")
public class CoffeeShopController {

    @Autowired
    private CoffeeShopService coffeeShopService;
    @Autowired
    private CoffeeShopRepository coffeeShopRepository;

    @PostMapping
    @PreAuthorize("hasAuthority('Global_Admin')")
    public ResponseEntity createShop(@RequestBody CoffeeShopCreateDTO coffeeShopRegisterDTO) {
        return new ResponseEntity<>(this.coffeeShopService.createCoffeeShop(coffeeShopRegisterDTO), HttpStatus.CREATED);
    }


//Only allow admin update them shop
    @PutMapping
    //@PreAuthorize("hasAuthority('Global_Admin')")
    public ResponseEntity updateShop(@RequestBody CoffeeShopCreateDTO coffeeShopRegisterDTO) {
        return new ResponseEntity<>(this.coffeeShopService.updateCoffeeShop(coffeeShopRegisterDTO), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCoffeeShop(@RequestParam(defaultValue = PageDefault.NO) int no,
                                              @RequestParam(defaultValue = PageDefault.LIMIT) int limit) {
        return ResponseEntity.ok(coffeeShopService.findAllActive(no, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCoffeeShopById(@PathVariable long id) {
        return ResponseEntity.ok(coffeeShopRepository.findById(id));
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchCoffeeShop(@RequestParam int no, @RequestParam int limit,
                                              @RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "location", required = false) String location){
        SearchCoffeeShopDTO searchCoffeeShopDTO = new SearchCoffeeShopDTO(name, location);
        return ResponseEntity.ok(coffeeShopService.searchCoffeeShop(searchCoffeeShopDTO, no, limit));
    }
}
