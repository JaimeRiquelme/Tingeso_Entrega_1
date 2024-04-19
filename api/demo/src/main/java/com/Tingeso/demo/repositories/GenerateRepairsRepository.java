package com.Tingeso.demo.repositories;

import com.Tingeso.demo.entities.GenerateRepairsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenerateRepairsRepository extends JpaRepository<GenerateRepairsEntity, Long>{

    @Query(value = "SELECT V.marca, AVG(EXTRACT(EPOCH FROM (R.fecha_salida_reparacion - R.fecha_ingreso_taller)) / 3600) AS Promedio_Horas FROM Reparaciones R JOIN Vehiculos V ON R.patente_vehiculo = V.patente GROUP BY V.marca", nativeQuery = true)
    public List<Object[]> getPromedioHorasPorMarca();

    //obtener una reparacion por el id de la reparacion con JPA
    public GenerateRepairsEntity findById(int idReparacion);

    /*
    *
    *
    * SELECT
    sub.tipo_reparacion_individual,
    COUNT(DISTINCT v.tipo) as numero_tipos_vehiculos_reparados,
    SUM(CASE
          WHEN v.tipo_motor = 'Diesel' THEN rt.cost_diesel
          WHEN v.tipo_motor = 'Electrico' THEN rt.cost_electric
          WHEN v.tipo_motor = 'Gasolina' THEN rt.cost_gasoline
          WHEN v.tipo_motor = 'Híbrido' THEN rt.cost_hybrid
          ELSE 0
        END) as monto_total
FROM
    (SELECT
         r.patente_vehiculo,
         CAST(UNNEST(STRING_TO_ARRAY(r.tipo_reparacion, ',')) AS bigint) AS tipo_reparacion_individual
     FROM
         reparaciones r) sub
JOIN
    vehiculos v ON sub.patente_vehiculo = v.patente
JOIN
    repairs_type rt ON sub.tipo_reparacion_individual = rt.id
GROUP BY
    sub.tipo_reparacion_individual
ORDER BY
    monto_total DESC;
*/

    @Query(value = "SELECT sub.tipo_reparacion_individual, COUNT(DISTINCT v.tipo) as numero_tipos_vehiculos_reparados, SUM(CASE WHEN v.tipo_motor = 'Diesel' THEN rt.cost_diesel WHEN v.tipo_motor = 'Electrico' THEN rt.cost_electric WHEN v.tipo_motor = 'Gasolina' THEN rt.cost_gasoline WHEN v.tipo_motor = 'Híbrido' THEN rt.cost_hybrid ELSE 0 END) as monto_total FROM (SELECT r.patente_vehiculo, CAST(UNNEST(STRING_TO_ARRAY(r.tipo_reparacion, ',')) AS bigint) AS tipo_reparacion_individual FROM reparaciones r) sub JOIN vehiculos v ON sub.patente_vehiculo = v.patente JOIN repairs_type rt ON sub.tipo_reparacion_individual = rt.id GROUP BY sub.tipo_reparacion_individual ORDER BY monto_total DESC", nativeQuery = true)
    public List<Object[]> GenerateGroupByTipoReparacion();





}
