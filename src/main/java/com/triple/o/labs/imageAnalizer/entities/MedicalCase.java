package com.triple.o.labs.imageAnalizer.entities;

import com.triple.o.labs.imageAnalizer.enums.AnalysisType;
import com.triple.o.labs.imageAnalizer.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

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

    @Column
    @Enumerated(EnumType.STRING)
    @Nullable
    private AnalysisType analysisType;

    @Lob
    @Column
    private String observations;

    @Column
    private String upperAppliance;

    @Column
    private String lowerAppliance;

    @ManyToOne
    @JoinColumn(name="patient_id", nullable=false)
    private Patient patient;

    @OneToOne
    private User user;

    @OneToOne
    private Image stl;

    @OneToOne()
    private Image medicalCaseImage;

    @OneToOne
    private Image snapshotImageAnalyzed;

    @OneToOne
    private Image bilmer;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "medicalCase")
    private List<Image> extraImages;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "medicalCase")
    private List<SchwarzKorkhausPairPoint> pairPoints;
}
