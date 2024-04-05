package com.Tingeso.demo.controllers;

import com.Tingeso.demo.services.SurchargeMileageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SurchargeMileageController {
    @Autowired
    SurchargeMileageServices surchargeMileageServices;
}
