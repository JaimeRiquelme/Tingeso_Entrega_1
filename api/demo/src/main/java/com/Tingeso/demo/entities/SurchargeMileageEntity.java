package com.Tingeso.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="SurchargeMileage")
@NoArgsConstructor
@AllArgsConstructor
@Data
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
