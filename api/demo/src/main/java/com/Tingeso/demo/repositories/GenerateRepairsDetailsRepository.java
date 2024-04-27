package com.Tingeso.demo.repositories;

import com.Tingeso.demo.entities.GenerateRepairsDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerateRepairsDetailsRepository extends JpaRepository<GenerateRepairsDetailsEntity, Long> {

    //buscar por tipo de reparacion_id
    @Query(value = "SELECT * FROM Detalle_Reparaciones WHERE reparacion_id = :idreparacion", nativeQuery = true)
    public GenerateRepairsDetailsEntity findByReparacion_id(@Param("idreparacion") int idreparacion);

   //guardar

    public GenerateRepairsDetailsEntity save(GenerateRepairsDetailsEntity details);

}
