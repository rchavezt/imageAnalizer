package com.triple.o.labs.imageAnalizer.dtos;

import com.triple.o.labs.imageAnalizer.entities.MedicalCaseImage;
import com.triple.o.labs.imageAnalizer.entities.Patient;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MedicalCaseDto {
    private Long id;
    private String detail;
    private Patient patient;
    private Set<MedicalCaseImage> medicalCaseImages;
}
