package com.Tingeso.demo.controllers;

import com.Tingeso.demo.services.SurchargeSeniorityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SurchargeSeniorityController {
    @Autowired
    SurchargeSeniorityServices surchargeSeniorityServices;
}
