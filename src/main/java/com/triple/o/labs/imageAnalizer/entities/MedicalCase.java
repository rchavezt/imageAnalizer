package com.triple.o.labs.imageAnalizer.entities;

import com.triple.o.labs.imageAnalizer.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Where(clause = "ACTIVE = 1")
public class MedicalCase extends UserTrackingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String detail;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name="patient_id", nullable=false)
    private Patient patient;

    @OneToOne
    private User user;

    @OneToOne
    private MedicalCaseImage medicalCaseImage;


}
