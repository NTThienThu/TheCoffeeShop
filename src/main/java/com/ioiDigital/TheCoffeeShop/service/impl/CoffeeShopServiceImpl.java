package com.ioiDigital.TheCoffeeShop.service.impl;

import com.ioiDigital.TheCoffeeShop.Specification.CoffeeShopSpecification;
import com.ioiDigital.TheCoffeeShop.dto.request.CoffeeShopCreateDTO;
import com.ioiDigital.TheCoffeeShop.dto.request.SearchCoffeeShopDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.CoffeeShopResponseDTO;
import com.ioiDigital.TheCoffeeShop.dto.response.PaginationDTO;
import com.ioiDigital.TheCoffeeShop.entity.Admin;
import com.ioiDigital.TheCoffeeShop.entity.CoffeeShop;
import com.ioiDigital.TheCoffeeShop.entity.Queue;
import com.ioiDigital.TheCoffeeShop.entity.User;
import com.ioiDigital.TheCoffeeShop.mapper.CoffeeShopMapper;
import com.ioiDigital.TheCoffeeShop.repository.AdminRepository;
import com.ioiDigital.TheCoffeeShop.repository.CoffeeShopRepository;
import com.ioiDigital.TheCoffeeShop.repository.QueueRepository;
import com.ioiDigital.TheCoffeeShop.repository.UserRepository;
import com.ioiDigital.TheCoffeeShop.service.AdminService;
import com.ioiDigital.TheCoffeeShop.service.CoffeeShopService;
import com.ioiDigital.TheCoffeeShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CoffeeShopServiceImpl implements CoffeeShopService {
    @Autowired
    private CoffeeShopRepository coffeeShopRepository;
    @Autowired
    private CoffeeShopMapper coffeeShopMapper;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private QueueRepository queueRepository;

    // set schedule for update status endDate once a day (0h00p)
//    @PostConstruct
//    @Scheduled(cron = "0 */15 * * * *")
//    public void updateWorkingStatus() {
//        List<CoffeeShop> coffeeShops = coffeeShopRepository.findByStatus(true);
//        LocalTime currentTime = LocalTime.now();
//
//        for (CoffeeShop coffeeShop : coffeeShops) {
//
//            if (currentTime.isAfter(coffeeShop.getOpeningTime()) && currentTime.isBefore(coffeeShop.getClosingTime())) {
//                coffeeShop.setWorkingStatus(true);
//            } else {
//                coffeeShop.setWorkingStatus(false);
//            }
//            coffeeShopRepository.save(coffeeShop);
//        }
//    }

    @Override
    public CoffeeShopResponseDTO createCoffeeShop(CoffeeShopCreateDTO coffeeShopRegisterDTO) {
        if (!this.coffeeShopRepository.existsByName(coffeeShopRegisterDTO.getName())) {
            CoffeeShop coffeeShop = this.coffeeShopMapper.toEntity(coffeeShopRegisterDTO);

            User user = userService.getCurrentLoginUser();
            coffeeShop.setCreatedBy(user.getId());
            coffeeShop.setStatus(true);

            //default maxSize = 10
            Queue queue = new Queue();
            queue.setMaxQueueSize(10);
            queue.setCoffeeShop(coffeeShop);

            coffeeShopRepository.save(coffeeShop);
            queueRepository.save(queue);

            return this.coffeeShopMapper.toDTO(coffeeShop);
        }
        throw new IllegalArgumentException("Shop name is exist");
    }

    //Not allow update name
    @Override
    public CoffeeShopResponseDTO updateCoffeeShop(CoffeeShopCreateDTO coffeeShopCreateDTO) {

        Admin admin = adminService.getCurrentLogInAdmin();
        CoffeeShop coffeeShop = coffeeShopRepository.findById(admin.getCoffeeShop().getId());
        coffeeShop.setLocation(coffeeShopCreateDTO.getLocation());
        coffeeShop.setContactDetails(coffeeShopCreateDTO.getContactDetails());
        coffeeShop.setOpeningTime(coffeeShopCreateDTO.getOpeningTime());
        coffeeShop.setClosingTime(coffeeShopCreateDTO.getClosingTime());
        coffeeShopRepository.save(coffeeShop);

        return this.coffeeShopMapper.toDTO(coffeeShop);
    }

    @Override
    public PaginationDTO findAllActive(int no, int limit) {
        Page<CoffeeShopResponseDTO> page =coffeeShopRepository.findAllActive(PageRequest.of(no, limit)).map(item -> coffeeShopMapper.toDTO(item));
        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(), page.getTotalPages(),
                page.getTotalElements(), page.getSize(), page.getNumber());
    }

    @Override
    public PaginationDTO searchCoffeeShop(SearchCoffeeShopDTO searchCoffeeShopDTO, int no, int limit) {
        Page<CoffeeShopResponseDTO> page = coffeeShopRepository.findAll(CoffeeShopSpecification.searchCoffeShopForCustomer(searchCoffeeShopDTO), PageRequest.of(no, limit))
                .map(item -> coffeeShopMapper.toDTO(item));
        return new PaginationDTO(page.getContent(), page.isFirst(), page.isLast(),
                page.getTotalPages(),
                page.getTotalElements(), page.getSize(),
                page.getNumber());
    }
}
