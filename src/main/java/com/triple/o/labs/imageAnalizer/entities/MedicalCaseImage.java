package com.triple.o.labs.imageAnalizer.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Where(clause = "ACTIVE = 1")
public class MedicalCaseImage extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String path;

    @ManyToOne
    @JoinColumn(name="medical_case_id", nullable=false)
    private MedicalCase medicalCase;

}
