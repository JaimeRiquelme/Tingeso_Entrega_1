package com.Tingeso.demo.services;

import com.Tingeso.demo.entities.GenerateRepairsEntity;
import com.Tingeso.demo.repositories.GenerateRepairsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GenerateRepairsServices {

    @Autowired
    GenerateRepairsRepository generateRepairsRepository;

    //Metodo para obtener todas las reparaciones generadas
    public ArrayList<GenerateRepairsEntity> getGenerateRepairs(){
        return (ArrayList<GenerateRepairsEntity>) generateRepairsRepository.findAll();
    }

    //Metodo para guardar una reparacion generada en la BD

    public GenerateRepairsEntity saveGenerateRepairs(GenerateRepairsEntity generateRepairs){
        return generateRepairsRepository.save(generateRepairs);
    }
}
