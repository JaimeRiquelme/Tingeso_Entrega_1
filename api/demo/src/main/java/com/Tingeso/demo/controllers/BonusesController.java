package com.Tingeso.demo.controllers;

import com.Tingeso.demo.entities.BonusesEntity;
import com.Tingeso.demo.services.BonusesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/bonuses")
@CrossOrigin
public class BonusesController {
    @Autowired
    BonusesServices bonusesServices;

    @GetMapping("/")
    public ResponseEntity<List<BonusesEntity>> listBonuses() {
        List<BonusesEntity> bonuses = bonusesServices.getBonuses();
        return ResponseEntity.ok(bonuses);
    }

    @PostMapping("/")
    public ResponseEntity<BonusesEntity> saveBonus(@RequestBody BonusesEntity bonus) {
        BonusesEntity bonusNew = bonusesServices.saveBonus(bonus);
        return ResponseEntity.ok(bonusNew);
    }
}
