package com.Tingeso.demo.services;

import com.Tingeso.demo.entities.DiscountRepairsEntity;
import com.Tingeso.demo.repositories.DiscountRepairsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountRepairsServices {
    @Autowired
    DiscountRepairsRepository discountRepairsRepository;

    public DiscountRepairsEntity findByIdDiscount(int idDiscount){
        return discountRepairsRepository.findByIdDiscount(idDiscount);
    }

    public DiscountRepairsEntity saveDiscountRepairs(DiscountRepairsEntity discountRepairsEntity){
        return discountRepairsRepository.save(discountRepairsEntity);
    }
}
