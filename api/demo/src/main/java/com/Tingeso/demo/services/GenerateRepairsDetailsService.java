package com.Tingeso.demo.services;

import com.Tingeso.demo.entities.GenerateRepairsDetailsEntity;
import com.Tingeso.demo.repositories.GenerateRepairsDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenerateRepairsDetailsService {

    @Autowired
    GenerateRepairsDetailsRepository generateRepairsDetailsRepository;

    public GenerateRepairsDetailsEntity findByReparacionId(int id_reparacion){
        return generateRepairsDetailsRepository.findByReparacion_id(id_reparacion);
    }
}
