package com.ioiDigital.TheCoffeeShop.specification;

import com.ioiDigital.TheCoffeeShop.dto.request.SearchCoffeeShopDTO;
import com.ioiDigital.TheCoffeeShop.entity.CoffeeShop;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CoffeeShopSpecification implements Specification<CoffeeShop> {
    @Override
    public Predicate toPredicate(Root<CoffeeShop> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }

    public static Specification<CoffeeShop> searchCoffeeShopForCustomer(SearchCoffeeShopDTO searchCoffeeShopDTO){
        return (root, query, cb) -> {

           List<Predicate> predicates = new ArrayList<>();

            if(searchCoffeeShopDTO.getName() != null){
                String name = searchCoffeeShopDTO.getName();
                predicates.add(cb.like(cb.function("LOWER", String.class, root.<String>get("name")), "%" + name.toLowerCase() + "%"));
            }

            if(searchCoffeeShopDTO.getLocation() != null){
                String location = searchCoffeeShopDTO.getLocation();
                predicates.add(cb.like(cb.function("LOWER", String.class, root.<String>get("location")), "%" + location.toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}
