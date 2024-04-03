package com.Tingeso.demo.entities;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

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

    private String tipo_reparacion; //Aqui podriamos relacionar con otra tabla

    private Float monto_total_reparacion;

    private LocalDateTime fecha_salida_reparacion;

    private LocalTime hora_salida_reparacion;

    private LocalDateTime fecha_entrega_cliente;

    private LocalTime hora_entrega_cliente;

    private String patente_vehiculo; //Aqui podriamos relacionar con otra tabla
}
