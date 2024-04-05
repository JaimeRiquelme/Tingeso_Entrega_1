package com.Tingeso.demo.entities;

import jakarta.persistence.*;

@Entity
public class SurchargeMileageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    private int kilometraje_minimo;

    private int kilometraje_maximo;

    private float sedan;

    private float hatchback;

    private float suv;

    private float pickup;

    private float furgoneta;
}
