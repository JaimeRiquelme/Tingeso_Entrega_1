package com.Tingeso.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class RepairsCountEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    private String patente;

    private int cantidad_reparaciones;
}
