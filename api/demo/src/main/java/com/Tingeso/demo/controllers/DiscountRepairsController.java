package com.Tingeso.demo.controllers;

import com.Tingeso.demo.services.DiscountRepairsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class DiscountRepairsController {
    @Autowired
    DiscountRepairsServices discountRepairsServices;
}
