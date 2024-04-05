package com.Tingeso.demo.services;

import com.Tingeso.demo.repositories.DiscountRepairsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountRepairsServices {
    @Autowired
    DiscountRepairsRepository discountRepairsRepository;
}
