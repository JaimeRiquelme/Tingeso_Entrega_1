package com.Tingeso.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="DetalleReparaciones")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GenerateRepairsDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    private long reparacion_id;

    private Float total_descuento;

    private Float total_recargos;

    private Float descuento_bono;

    private Float iva;

    private Float monto_reparaciones;

    private Float total;


}
