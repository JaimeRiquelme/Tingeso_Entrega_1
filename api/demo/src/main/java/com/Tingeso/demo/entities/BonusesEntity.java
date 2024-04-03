package com.Tingeso.demo.entities;

import ch.qos.logback.core.joran.action.AppenderRefAction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeBinderType;

@Entity
@Table(name="Bonuses")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BonusesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    private String marca;

    private String disponibilidad;

    private float monto;
}
