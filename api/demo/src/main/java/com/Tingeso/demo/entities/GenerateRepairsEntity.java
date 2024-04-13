package com.Tingeso.demo.entities;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name="Reparaciones")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GenerateRepairsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    private LocalDateTime fecha_ingreso_taller;

    private LocalTime hora_ingreso_taller;

    //Como puede tener mas de un tipo de reparacion, podriamos hacer una lista de ids de reparaciones
    private String tipo_reparacion;

    private Float monto_total_reparacion;

    private LocalDateTime fecha_salida_reparacion;

    private LocalTime hora_salida_reparacion;

    private LocalDateTime fecha_entrega_cliente;

    private LocalTime hora_entrega_cliente;

    private String patente_vehiculo; //Aqui podriamos relacionar con otra tabla


}
