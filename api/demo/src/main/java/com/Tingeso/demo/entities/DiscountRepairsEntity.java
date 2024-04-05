package com.Tingeso.demo.entities;

import jakarta.persistence.*;

@Entity
public class DiscountRepairsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    private int reparaciones_minimas;

    private int reparaciones_maximas;

    private float gasolina;

    private float diesel;

    private float hibirido;

    private float electrico;

}
