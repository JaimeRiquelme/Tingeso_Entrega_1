package com.Tingeso.demo.services;

import com.Tingeso.demo.entities.VehiclesEntity;
import com.Tingeso.demo.repositories.VehiclesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VehiclesServices {

    @Autowired
    VehiclesRepository vehiclesRepository;

    //Metodo para obtener todos los vehiculos
    public ArrayList<VehiclesEntity> getVehicles(){
        return (ArrayList<VehiclesEntity>) vehiclesRepository.findAll();
    }

    //Metodo para guardar un vehiculo en la BD
    public VehiclesEntity saveVehicle(VehiclesEntity vehicle){
        return vehiclesRepository.save(vehicle);
    }

    public VehiclesEntity getVehiclesByPatente(String patente){
        return vehiclesRepository.findByPatente(patente);
    }

    public VehiclesEntity updateVehicle(VehiclesEntity vehicle){
        return vehiclesRepository.save(vehicle);
    }
    public void deleteVehicle(long id){
        vehiclesRepository.deleteById(id);
    }
}
