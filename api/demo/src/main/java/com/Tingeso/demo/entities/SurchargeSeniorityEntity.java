package com.Tingeso.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="SurchargeSeniority")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SurchargeSeniorityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    private int years_min;

    private int years_max;

    private float sedan;

    private float hatchback;

    private float suv;

    private float pickup;

    private float furgoneta;
}
