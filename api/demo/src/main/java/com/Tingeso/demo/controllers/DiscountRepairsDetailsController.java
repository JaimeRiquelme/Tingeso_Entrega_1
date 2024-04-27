package com.Tingeso.demo.controllers;

import com.Tingeso.demo.entities.GenerateRepairsDetailsEntity;
import com.Tingeso.demo.services.GenerateRepairsDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/discountrepairsdetails")
@CrossOrigin("*")
public class DiscountRepairsDetailsController {

    @Autowired
    GenerateRepairsDetailsService generateRepairsDetailsService;

    @GetMapping("/{id}")
    public GenerateRepairsDetailsEntity findById_reparacion(@PathVariable("id") int id_reparacion) {
        return generateRepairsDetailsService.findByReparacionId(id_reparacion);
    }
}
