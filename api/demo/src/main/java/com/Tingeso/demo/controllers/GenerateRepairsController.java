package com.Tingeso.demo.controllers;

import com.Tingeso.demo.entities.GenerateRepairsEntity;
import com.Tingeso.demo.services.GenerateRepairsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/generateRepairs")
@CrossOrigin("*")
public class GenerateRepairsController {

    @Autowired
    GenerateRepairsServices generateRepairsServices;

    @GetMapping("/")
    public ResponseEntity<List<GenerateRepairsEntity>> listGenerateRepairs() {
        List<GenerateRepairsEntity> generateRepairs = generateRepairsServices.getGenerateRepairs();
        return ResponseEntity.ok(generateRepairs);
    }

    @PostMapping("/")
    public ResponseEntity<GenerateRepairsEntity> saveGenerateRepair(@RequestBody GenerateRepairsEntity generateRepair) {
        GenerateRepairsEntity generateRepairNew = generateRepairsServices.saveGenerateRepairs(generateRepair);
        return ResponseEntity.ok(generateRepairNew);
    }
}
