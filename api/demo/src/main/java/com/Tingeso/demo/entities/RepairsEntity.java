package com.Tingeso.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="RepairsType")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RepairsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    private String type;

    private String description;

    private float cost_gasoline;

    private float cost_diesel;

    private float cost_hybrid;

    private float cost_electric;

}
