package com.Tingeso.demo.services;

import com.Tingeso.demo.entities.RepairsCountEntity;
import com.Tingeso.demo.repositories.RepairsCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RepairsCountServices {
    @Autowired
    RepairsCountRepository repairsCountRepository;

    //Metodo para obtener todas las reparaciones generadas
    public ArrayList<RepairsCountEntity> getRepairsCount(){
        return (ArrayList<RepairsCountEntity>) repairsCountRepository.findAll();
    }

    //Metodo para guardar una reparacion generada en la BD
    public RepairsCountEntity saveRepairsCount(RepairsCountEntity repairsCount){
        return repairsCountRepository.save(repairsCount);
    }
}
