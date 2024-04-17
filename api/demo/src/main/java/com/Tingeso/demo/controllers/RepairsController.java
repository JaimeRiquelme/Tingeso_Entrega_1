package com.Tingeso.demo.controllers;

import com.Tingeso.demo.entities.RepairsEntity;
import com.Tingeso.demo.services.RepairsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/repairs")
@CrossOrigin("*")
public class RepairsController {
    @Autowired
    RepairsServices repairsServices;

    @GetMapping("/")
    public ResponseEntity<List<RepairsEntity>> listRepairs() {
        List<RepairsEntity> repairs = repairsServices.getRepairs();
        return ResponseEntity.ok(repairs);
    }

    @PostMapping("/")
    public ResponseEntity<RepairsEntity> saveRepair(@RequestBody RepairsEntity repair) {
        RepairsEntity repairNew = repairsServices.saveRepair(repair);
        return ResponseEntity.ok(repairNew);
    }

    @GetMapping("/types")
    public ResponseEntity<String[]> getTypes() {
        String[] types = repairsServices.getTypes();
        return ResponseEntity.ok(types);
    }

}
