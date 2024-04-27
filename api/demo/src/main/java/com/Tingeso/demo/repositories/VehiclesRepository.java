package com.Tingeso.demo.repositories;

import com.Tingeso.demo.entities.VehiclesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface VehiclesRepository extends JpaRepository<VehiclesEntity, Long> {

    VehiclesEntity findByPatente(String patente);

    @Query(value = "SELECT v.patente FROM VehiclesEntity v")
    String[] findAllPatente();

    public ArrayList<VehiclesEntity> findAll();

    public void deleteById(long id);



}
