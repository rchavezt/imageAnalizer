package com.triple.o.labs.imageAnalizer.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
//@Where(clause = "ACTIVE = 1")
public class SchwarzKorkhausPairPoint {

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

    @Column
    private int sort;

    @ManyToOne
    @JoinColumn(name = "fk_medical_case")
    private MedicalCase medicalCase;
}
