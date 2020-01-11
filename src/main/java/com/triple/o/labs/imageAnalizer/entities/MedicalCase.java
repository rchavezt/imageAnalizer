package com.triple.o.labs.imageAnalizer.entities;

import com.triple.o.labs.imageAnalizer.enums.AnalysisType;
import com.triple.o.labs.imageAnalizer.enums.ImageType;
import com.triple.o.labs.imageAnalizer.enums.Status;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

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

    @OneToOne
    private Image medicalCaseImage;

    @OneToOne
    private Image snapshotImageAnalyzed;

    @OneToOne
    private Image bilmer;

    @OneToOne
    private Image canvas;

    @OneToOne
    private Image analyzedBlue;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Image> extraImages;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "medicalCase")
    private List<SchwarzKorkhausPairPoint> pairPoints;

    public List<Image> getExtraImages() {
        return extraImages.stream().filter( e -> e.getImageType() == ImageType.extra).collect(Collectors.toList());
    }
}
