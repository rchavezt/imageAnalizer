package com.triple.o.labs.imageAnalizer.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class SchwarzKorkhausPairPoint extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private float pointX;

    @Column
    private float pointY;

    @Column
    private boolean lineRelation;

    @Column
    private String color;

    @Column
    private boolean required;

    @Column
    private boolean extendedLine;

    @ManyToOne
    @JoinColumn(name = "fk_medical_case", nullable = false, updatable = false)
    private MedicalCase medicalCase;
}
