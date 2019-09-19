package com.triple.o.labs.imageAnalizer.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class SchwarzKorkhausPairPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private float pointAX;

    @Column
    private float pointAY;

    @Column
    private String nameA;

    @Column
    private float pointBX;

    @Column
    private float pointBY;

    @Column
    private String nameB;

    @Column
    private boolean required;

    @Column
    private String color;

    @ManyToOne
    @JoinColumn(name = "fk_medical_case", nullable = false, updatable = false)
    private MedicalCase medicalCase;
}
