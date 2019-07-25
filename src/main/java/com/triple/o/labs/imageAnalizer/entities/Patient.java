package com.triple.o.labs.imageAnalizer.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Where(clause = "ACTIVE = 1")
public class Patient extends UserTrackingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    String email;

    @ManyToOne
    @JoinColumn(nullable=false)
    private User doctorUser;

    @OneToMany
    private Set<MedicalCase> medicalCases;
}
