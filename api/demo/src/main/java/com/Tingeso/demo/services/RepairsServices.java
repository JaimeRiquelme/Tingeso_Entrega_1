package com.Tingeso.demo.services;

import com.Tingeso.demo.entities.RepairsEntity;
import com.Tingeso.demo.repositories.RepairsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RepairsServices {

    @Autowired
    RepairsRepository repairsRepository;

    //Metodo para obtener todas las reparaciones
    public ArrayList<RepairsEntity> getRepairs(){
        return (ArrayList<RepairsEntity>) repairsRepository.findAll();
    }

    //Metodo para guardar una reparacion en la BD
    public RepairsEntity saveRepair(RepairsEntity repair){
        return repairsRepository.save(repair);
    }

    //Metodo para obtener los tipos de reparaciones
    public String[] getTypes(){
        return repairsRepository.getTypes();
    }

    //Metodo para obtener una reparacion por su id
    public RepairsEntity getRepairById(Long id) {
        return repairsRepository.findById(id).orElse(null);
    }

}
