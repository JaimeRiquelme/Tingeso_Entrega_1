package com.Tingeso.demo.controllers;

import com.Tingeso.demo.entities.VehiclesEntity;
import com.Tingeso.demo.services.VehiclesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/vehicles")
@CrossOrigin("*")
public class VehiclesController {

    @Autowired
    VehiclesServices vehiclesServices;

    @GetMapping("/")
    public ResponseEntity<List<VehiclesEntity>> listVehicles() {
        List<VehiclesEntity> vehicles = vehiclesServices.getVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping("/")
    public ResponseEntity<VehiclesEntity> saveVehicle(@RequestBody VehiclesEntity vehicle) {
        VehiclesEntity vehicleNew = vehiclesServices.saveVehicle(vehicle);
        return ResponseEntity.ok(vehicleNew);
    }
}
