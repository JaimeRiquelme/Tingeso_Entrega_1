package com.Tingeso.demo.repositories;

import com.Tingeso.demo.entities.VehiclesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiclesRepository extends JpaRepository<VehiclesEntity, Long> {


    //Encontrar vehiculo por su patente
    VehiclesEntity findByPatente(String patente);



}
