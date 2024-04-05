package com.Tingeso.demo.services;

import com.Tingeso.demo.repositories.SurchargeMileageRepository;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurchargeMileageServices {
    @Autowired
    SurchargeMileageRepository surchargeMileageRepository;
}
