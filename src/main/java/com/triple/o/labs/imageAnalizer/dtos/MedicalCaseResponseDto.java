package com.triple.o.labs.imageAnalizer.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MedicalCaseResponseDto {
    private Long id;
    private String detail;
    private String doctorFullName;
    private PatientDto patient;
    private Set<String> medicalCaseImagesUrl;
}
