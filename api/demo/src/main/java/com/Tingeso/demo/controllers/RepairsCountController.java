package com.Tingeso.demo.controllers;

import com.Tingeso.demo.services.RepairsCountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/repairscount")
@CrossOrigin("*")
public class RepairsCountController {

    @Autowired
    RepairsCountServices repairsCountServices;

    @GetMapping("/")
    public ResponseEntity<List<r>>

}
