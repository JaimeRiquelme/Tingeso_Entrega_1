package com.Tingeso.demo.controllers;

import com.Tingeso.demo.entities.DiscountRepairsEntity;
import com.Tingeso.demo.services.DiscountRepairsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/discountrepairs")
@CrossOrigin("*")
public class DiscountRepairsController {
    @Autowired
    DiscountRepairsServices discountRepairsServices;

    @GetMapping("/{id}")
    public DiscountRepairsEntity findByIdDiscount(@PathVariable("id") int idDiscount) {
        return discountRepairsServices.findByIdDiscount(idDiscount);
    }


    @PostMapping("/")
    public DiscountRepairsEntity saveDiscountRepairs(@RequestBody DiscountRepairsEntity discountRepairsEntity){
        return discountRepairsServices.saveDiscountRepairs(discountRepairsEntity);
    }

}
