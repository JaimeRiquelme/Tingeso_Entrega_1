package com.Tingeso.demo.services;

import com.Tingeso.demo.repositories.SurchargeSeniorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurchargeSeniorityServices {
    @Autowired
    SurchargeSeniorityRepository surchargeSeniorityRepository;
}
