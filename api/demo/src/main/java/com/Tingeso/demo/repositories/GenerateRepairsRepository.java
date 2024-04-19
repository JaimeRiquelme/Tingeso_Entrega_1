package com.Tingeso.demo.repositories;

import com.Tingeso.demo.entities.GenerateRepairsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenerateRepairsRepository extends JpaRepository<GenerateRepairsEntity, Long>{

    /*SELECT
    V.marca,
    AVG(EXTRACT(EPOCH FROM (R.fecha_salida_reparacion - R.fecha_ingreso_taller)) / 3600) AS Promedio_Horas
FROM
    Reparaciones R
JOIN
    Vehiculos V ON R.patente_vehiculo = V.patente
GROUP BY
    V.marca;


     */
    @Query(value = "SELECT V.marca, AVG(EXTRACT(EPOCH FROM (R.fecha_salida_reparacion - R.fecha_ingreso_taller)) / 3600) AS Promedio_Horas FROM Reparaciones R JOIN Vehiculos V ON R.patente_vehiculo = V.patente GROUP BY V.marca", nativeQuery = true)
    public List<Object[]> getPromedioHorasPorMarca();




}
