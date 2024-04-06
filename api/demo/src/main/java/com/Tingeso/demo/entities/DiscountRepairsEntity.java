package com.Tingeso.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="DiscountRepairs")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DiscountRepairsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    private int reparaciones_minimas;

    private int reparaciones_maximas;

    private float gasolina;

    private float diesel;

    private float hibrido;

    private float electrico;

}
